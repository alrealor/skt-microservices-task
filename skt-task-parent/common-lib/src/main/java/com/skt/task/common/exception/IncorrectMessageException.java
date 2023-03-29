package com.skt.task.common.exception;

import lombok.Data;

/**
 * Class to represent a Business Exception
 */
@Data
public class IncorrectMessageException extends RuntimeException {

    private String errorCode;
    private String errorMessage;

    public IncorrectMessageException(String errorCode, String errorMessage) {
        super(errorCode + "-" + errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
