package com.skt.task.microservice.handler;

import com.skt.task.common.domain.BaseError;
import com.skt.task.common.domain.BaseResponse;
import com.skt.task.common.exception.BusinessException;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.lang.reflect.InvocationTargetException;

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
        return new ResponseEntity<>(this.buildErrorBaseResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
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

        return new ResponseEntity<>(this.buildErrorBaseResponse("SUN001-"+ex.getMessage()),
                HttpStatus.SERVICE_UNAVAILABLE);
    }

    /**
     * Method used to build an error base response to show into error response
     *
     * @param errorMessage of {@link String} type
     * @return base error response of {@link BaseResponse} type
     */
    private BaseResponse buildErrorBaseResponse(String errorMessage) {
        BaseResponse baseResponse = new BaseResponse();
        BaseError baseError = new BaseError();
        String[] split = errorMessage.split("-", 2);
        baseError.setErrorCode(split[0]);
        baseError.setErrorMessage(split[1]);
        baseResponse.setError(baseError);

        return baseResponse;
    }
}

