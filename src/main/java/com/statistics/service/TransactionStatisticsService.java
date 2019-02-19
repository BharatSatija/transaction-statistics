package com.statistics.service;

import com.statistics.entity.Statistics;
import com.statistics.entity.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface TransactionStatisticsService {

	/**
	 * To add a transaction
	 * @param transaction
	 * @return
	 */
	ResponseEntity<HttpStatus> addTransaction(Transaction transaction);

	/**
	 * To delete transactions
	 * @return
	 */
	ResponseEntity<HttpStatus> deleteTransactions();

	/**
	 * To get statistics
	 * @return
	 */
	Statistics getStatistics();

}
