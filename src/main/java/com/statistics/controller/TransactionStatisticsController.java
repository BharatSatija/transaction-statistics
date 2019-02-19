package com.statistics.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.statistics.entity.Transaction;
import com.statistics.entity.TransactionRequest;
import com.statistics.service.TransactionStatisticsService;
import com.statistics.utils.HelperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@RestController
public class TransactionStatisticsController {

	@Autowired
	private TransactionStatisticsService transactionStatisticsService;

	/**
	 * To add a Transaction
	 *
	 * @param transactionRequest
	 * @return
	 */
	@RequestMapping(value = "/transactions", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> addTransaction(@RequestBody TransactionRequest transactionRequest) {

		Transaction transaction = HelperUtil.parseRequest(transactionRequest);

		return transactionStatisticsService.addTransaction(transaction);
	}


	/**
	 * To delete all Transactions
	 *
	 * @return test
	 */
	@RequestMapping(value = "/transactions", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteTransactions() {

		return transactionStatisticsService.deleteTransactions();
	}


	/**
	 * To get statistics
	 * @return
	 */
	@RequestMapping(value = "/statistics", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> getStatistics() {
		return new ResponseEntity<>(transactionStatisticsService.getStatistics(), HttpStatus.OK);
	}


	@ExceptionHandler(HttpMessageNotReadableException.class)
	public final ResponseEntity<Void> httpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {
		if (ex.getCause() instanceof InvalidFormatException)
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);

		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}


}
