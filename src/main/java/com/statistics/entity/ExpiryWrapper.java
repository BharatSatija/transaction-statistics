package com.statistics.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * Wrapper object containing a value object & the timestamp at which the value
 * expires.
 * 
 * @param <T>
 */
public class ExpiryWrapper<T> {

	private T value;
	private long expiryTimestamp;

	public T getValue() {
		return value;
	}

	public ExpiryWrapper<T> setValue(T value) {
		this.value = value;
		return this;
	}

	public long getExpiryTimestamp() {
		return expiryTimestamp;
	}

	public ExpiryWrapper<T> setExpiryTimestamp(long expiryTimestamp) {
		this.expiryTimestamp = expiryTimestamp;
		return this;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
