package com.skt.task.microservice.handler;

import com.skt.task.common.domain.BaseResponse;
import com.skt.task.common.exception.BusinessException;
import org.hibernate.exception.SQLGrammarException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

import static com.skt.task.common.constants.ErrorCodes.PRODUCT_NOT_CREATED;
import static com.skt.task.common.constants.ErrorCodes.STANDARD_CONNECTION_CODE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * test class for default exception handler
 */
public class DefaultExceptionHandlerTest {

    private DefaultExceptionHandler exceptionHandler;

    @Before
    public void setup() {
        this.exceptionHandler = new DefaultExceptionHandler();
    }

    @Test
    public void test_handleBusinessExceptions_success() {

        BusinessException ex = new BusinessException(PRODUCT_NOT_CREATED, "Business exception message");

        ResponseEntity<BaseResponse> result = this.exceptionHandler.handleBusinessExceptions(ex);

        assertNotNull(result);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        assertEquals(PRODUCT_NOT_CREATED, result.getBody().getError().getErrorCode());
        assertEquals("Business exception message", result.getBody().getError().getErrorMessage());
    }

    @Test
    public void test_handleConnectionExceptions_success() {
        SQLException sqlEx = new SQLException("SQL exception message");
        SQLGrammarException sqlGrammarEx = new SQLGrammarException("SQL grammar exception message", sqlEx);

        ResponseEntity<BaseResponse> result = this.exceptionHandler.handleConnectionExceptions(sqlGrammarEx);

        assertNotNull(result);
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, result.getStatusCode());
        assertEquals(STANDARD_CONNECTION_CODE, result.getBody().getError().getErrorCode());
        assertEquals("SQL grammar exception message", result.getBody().getError().getErrorMessage());
    }
}