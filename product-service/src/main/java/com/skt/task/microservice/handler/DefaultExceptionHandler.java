package com.skt.task.microservice.handler;

import com.skt.task.common.domain.BaseError;
import com.skt.task.common.domain.BaseResponse;
import com.skt.task.common.exception.BusinessException;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.lang.reflect.InvocationTargetException;

import static com.skt.task.common.constants.ErrorCodes.STANDARD_CONNECTION_CODE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

/**
 * Class to centralize the management of exceptions
 */
@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Exception handler method for internal server error response
     *
     * @param ex of {@link Exception} type
     * @return response entity wit error of {@link ResponseEntity} type
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BaseResponse> handleBusinessExceptions(BusinessException ex) {
        return new ResponseEntity<>(
                this.buildErrorBaseResponse(ex.getErrorCode(), ex.getErrorMessage()),
                INTERNAL_SERVER_ERROR);
    }

    /**
     * Exception handler method for service unavailable response
     *
     * @param ex of {@link Exception} type
     * @return response entity wit error of {@link ResponseEntity} type
     */
    @ExceptionHandler(value = {IllegalStateException.class,
                               InvocationTargetException.class,
                               JDBCConnectionException.class,
                               SQLGrammarException.class})
    public ResponseEntity<BaseResponse> handleConnectionExceptions(Exception ex) {

        return new ResponseEntity<>(
                this.buildErrorBaseResponse(STANDARD_CONNECTION_CODE, ex.getMessage()),
                SERVICE_UNAVAILABLE);
    }

    /**
     * Method used to build an error base response to show into error response
     *
     * @param errorMessage of {@link String} type
     * @return base error response of {@link BaseResponse} type
     */
    private BaseResponse buildErrorBaseResponse(String errorCode, String errorMessage) {
        BaseResponse baseResponse = new BaseResponse();
        BaseError baseError = new BaseError();
        baseError.setErrorCode(errorCode);
        baseError.setErrorMessage(errorMessage);
        baseResponse.setError(baseError);

        return baseResponse;
    }
}

