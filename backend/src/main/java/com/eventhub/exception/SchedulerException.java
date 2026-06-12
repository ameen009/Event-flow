package com.eventhub.exception;

public class SchedulerException extends RuntimeException {

    public SchedulerException(String message, Throwable cause) {
        super(message, cause);
    }
}
