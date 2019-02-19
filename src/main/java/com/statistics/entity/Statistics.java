package com.statistics.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * Object containing the transaction statistics.
 *
 */
@Getter
@Setter
public class Statistics implements Serializable {

	private static final long serialVersionUID = 1L;

	private String sum;
	private String avg;
	private String max;
	private String min;
	private long count;

	public Statistics(){
	}

	public Statistics(String sum, String avg, String max, String min, long count) {
		this.sum = sum;
		this.avg = avg;
		this.max = max;
		this.min = min;
		this.count = count;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
