package com.statistics.service.impl;


import com.statistics.entity.ExpiryWrapper;
import com.statistics.entity.Statistics;
import com.statistics.entity.Transaction;
import com.statistics.service.TransactionStatisticsService;
import com.statistics.utils.HelperUtil;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TransactionStatisticsServiceImpl implements TransactionStatisticsService {
	private List<ExpiryWrapper<Transaction>> transactionsWithExpiry = Collections.synchronizedList(new ArrayList<ExpiryWrapper<Transaction>>());
	private AtomicReference<Statistics> atomicStatistics = new AtomicReference<Statistics>(new Statistics());

	public TransactionStatisticsServiceImpl() {
	}

	public ResponseEntity<HttpStatus> addTransaction(Transaction transaction) {
		Instant now = Instant.now();
		if(!HelperUtil.validateForOlderTransactionTimestamp(transaction, now)) {
			return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
		} else if(!HelperUtil.validateForFutureTransactionTimestamp(transaction, now)) {
			return new ResponseEntity<HttpStatus>(HttpStatus.UNPROCESSABLE_ENTITY);
		} else {
			transactionsWithExpiry.add(new ExpiryWrapper<Transaction>().setValue(transaction).setExpiryTimestamp(transaction.getTimeStamp().toEpochMilli() + 60000L));
			return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
		}
	}

	public Statistics getStatistics() {
		return atomicStatistics.get();
	}

	@Scheduled(
			fixedRate = 1000L
	)
	private void cleanupExpiredTransactionsAndComputeStatistics() {
		List<ExpiryWrapper<Transaction>> transactions;

		synchronized(transactionsWithExpiry) {
			transactionsWithExpiry = transactionsWithExpiry.parallelStream()
					                                .filter(transaction -> transaction.getExpiryTimestamp() > Instant.now().toEpochMilli())
					                                .collect(Collectors.toList());
			                        transactions = new ArrayList<>(transactionsWithExpiry);
		}

		List<BigDecimal> transaction_amount_list = transactions.parallelStream().map(t ->
			t.getValue().getAmount()).collect(Collectors.toList());

		long count = transaction_amount_list.size();

		if(count <= 0L) {
			atomicStatistics.set(new Statistics("0.00", "0.00", "0.00", "0.00", 0L));
		} else {
			BigDecimal sum = transaction_amount_list.parallelStream().reduce(BigDecimal.ZERO, BigDecimal::add);
			BigDecimal max = transaction_amount_list.parallelStream().max(Comparator.naturalOrder()).get();
			BigDecimal min = transaction_amount_list.parallelStream().min(Comparator.naturalOrder()).get();
			BigDecimal avg = sum.divide(new BigDecimal(transaction_amount_list.size()), 4);
			atomicStatistics.set(new Statistics(round(sum), avg.toString(), round(max), round(min), count));
		}

	}

	public ResponseEntity<HttpStatus> deleteTransactions() {
		transactionsWithExpiry.clear();
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	}

	private String round(BigDecimal value) {
		return value.setScale(2, 4).toString();
	}
}
