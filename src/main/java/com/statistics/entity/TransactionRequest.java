package com.statistics.entity;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.Instant;


@Getter
@Setter
public class TransactionRequest {

	@NotNull
	private String amount;

	@NotNull
	private String timestamp;

	public TransactionRequest(){

	}


}
