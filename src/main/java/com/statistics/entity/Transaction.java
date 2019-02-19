package com.statistics.entity;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;


/**
 * Object containing the transaction.
 *
 */
@Getter
@Setter
public class Transaction implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal amount;
	private Instant timeStamp;

	public Transaction(){

	}

	public Transaction(BigDecimal amount, Instant timeStamp) {
		this.amount = amount;
		this.timeStamp = timeStamp;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
