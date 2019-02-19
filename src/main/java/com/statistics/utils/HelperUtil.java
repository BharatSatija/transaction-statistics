package com.statistics.utils;


import com.statistics.entity.Transaction;
import com.statistics.entity.TransactionRequest;
import com.statistics.exception.TransactionParseException;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.Instant;

public class HelperUtil {



    public static boolean validateForOlderTransactionTimestamp(Transaction transaction, Instant now) {
        return now.toEpochMilli() - transaction.getTimeStamp().toEpochMilli() <= Constants.TIMELIMIT_IN_EPOCH_MILLIS;
    }

    public static boolean validateForFutureTransactionTimestamp(Transaction transaction, Instant now) {
        return !transaction.getTimeStamp().isAfter(now);
    }


    public static Transaction parseRequest(TransactionRequest transactionRequest) throws TransactionParseException {
        try {
            return new Transaction(new BigDecimal(transactionRequest.getAmount()), Instant.parse(transactionRequest.getTimestamp()));
        } catch (NumberFormatException | DateTimeException e) {
            throw new TransactionParseException();
        }
    }


}
