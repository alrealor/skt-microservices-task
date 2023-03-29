package com.skt.task.common.exception;

import lombok.Data;

/**
 * Class to represent a Business Exception
 */
@Data
public class BusinessException extends Exception {

    private String errorCode;
    private String errorMessage;

    public BusinessException(String errorCode, String errorMessage) {
        super(errorCode + " - " + errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
