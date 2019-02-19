package com.statistics.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class TransactionParseException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -4568076212407650933L;

    public TransactionParseException() {
        super();
    }
}
