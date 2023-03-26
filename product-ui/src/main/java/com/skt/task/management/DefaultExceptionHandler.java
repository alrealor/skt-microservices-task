package com.skt.task.management;

import com.skt.task.common.domain.BaseError;
import com.skt.task.common.domain.BaseResponse;
import com.skt.task.common.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Exception handler method for business exception type
     *
     * @param ex exception ot type {@link BusinessException}
     * @return response entity
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BaseResponse> handleBusinessExceptions(BusinessException ex) {
        return new ResponseEntity<>(this.buildErrorBaseResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Method that builds a error response message
     *
     * @param errorMessage error message of {@link String} type
     * @return base response
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
