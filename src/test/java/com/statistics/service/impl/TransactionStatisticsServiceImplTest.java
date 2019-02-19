package com.statistics.service.impl;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Random;

import com.statistics.ApplicationTests;
import com.statistics.entity.Statistics;
import com.statistics.entity.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class TransactionStatisticsServiceImplTest extends ApplicationTests {

	@InjectMocks
	private TransactionStatisticsServiceImpl transactionStatisticsService;

	private Random random;


	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		random = new Random();
	}

	@Test
	public void addTransactionThatHappenedInLast60Sec() {
		Transaction transaction = new Transaction();
		transaction.setAmount(new BigDecimal(random.nextDouble()));
		transaction.setTimeStamp(Instant.now().minusMillis(10000));

		ResponseEntity<HttpStatus> responseEntity = transactionStatisticsService.addTransaction(transaction);

		Assert.assertEquals(responseEntity.getStatusCodeValue(), HttpStatus.CREATED.value());
	}

	@Test
	public void rejectTransactionThatHappened60SecAgo() {
		Transaction transaction = new Transaction();
		transaction.setAmount(new BigDecimal(random.nextDouble()));
		transaction.setTimeStamp(Instant.now().minusMillis(70000));

		ResponseEntity<HttpStatus> responseEntity = transactionStatisticsService.addTransaction(transaction);

		Assert.assertEquals(responseEntity.getStatusCodeValue(), HttpStatus.NO_CONTENT.value());
	}

	@Test
	public void getStatisticsReturnsNotNullResult() {
		Statistics statistics = transactionStatisticsService.getStatistics();
		Assert.assertNotNull(statistics);
	}

}
