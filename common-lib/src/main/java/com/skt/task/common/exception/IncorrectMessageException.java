package com.skt.task.common.exception;

/**
 * Class to represent a Business Exception
 */
public class IncorrectMessageException extends RuntimeException {
    public IncorrectMessageException(String message) {
        super(message);
    }
}
