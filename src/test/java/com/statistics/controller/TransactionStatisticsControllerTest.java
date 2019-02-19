package com.statistics.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Random;

import com.statistics.entity.Statistics;
import com.statistics.entity.Transaction;
import com.statistics.service.impl.TransactionStatisticsServiceImpl;
import com.statistics.ApplicationTests;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;

public class TransactionStatisticsControllerTest extends ApplicationTests {


	private MockMvc mockMvc;

	private Gson gson;

	private Random random;

	@Mock
	private TransactionStatisticsServiceImpl transactionStatisticsService;

	@InjectMocks
	private TransactionStatisticsController transactionStatisticsController;



	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(transactionStatisticsController).build();
		MockitoAnnotations.initMocks(this);
		gson = new Gson();
		random = new Random();
	}

	@Test
	public void returnCreatedStatusIfTransactionIsAdded() throws Exception {
		Transaction transaction = new Transaction();
		transaction.setAmount(new BigDecimal(random.nextDouble()));
		transaction.setTimeStamp(Instant.now().minusMillis(10000));

		when(transactionStatisticsService.addTransaction(any(Transaction.class))).thenReturn(new ResponseEntity<>(HttpStatus.CREATED));

		mockMvc.perform(
				post("/transactions").content(gson.toJson(transaction)).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isCreated());
	}


	@Test
	public void returnNoContentStatusIfTransactionIsRejected() throws Exception {
		Transaction transaction = new Transaction();
		transaction.setAmount(new BigDecimal(random.nextDouble()));
		transaction.setTimeStamp(Instant.now().minusMillis(70000));

		when(transactionStatisticsService.addTransaction(any(Transaction.class))).thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));

		mockMvc.perform(
				post("/transactions").contentType(MediaType.APPLICATION_JSON_VALUE).content(gson.toJson(transaction)))
				.andExpect(status().isNoContent());
	}


	@Test
	public void returnNoContentIfTransactionIsDeleted() throws Exception {


		when(transactionStatisticsService.deleteTransactions()).thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));

		mockMvc.perform(delete("/transactions"))
				.andExpect(status().isNoContent());
	}


	@Test
	public void getStatisticsReturnsNotNullResult() throws Exception {

		when(transactionStatisticsService.getStatistics()).thenReturn(new Statistics());

		MvcResult mvcResult = mockMvc.perform(get("/statistics")).andExpect(status().isOk()).andReturn();

		Statistics statistics = gson.fromJson(mvcResult.getResponse().getContentAsString(), Statistics.class);
		Assert.assertNotNull(statistics);
	}

}
