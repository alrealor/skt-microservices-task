package com.skt.task.microservice.handler;

import com.skt.task.common.domain.BaseResponse;
import com.skt.task.common.exception.BusinessException;
import org.hibernate.exception.SQLGrammarException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

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

        BusinessException ex = new BusinessException("CODE0-Business exception message");

        ResponseEntity<BaseResponse> result = this.exceptionHandler.handleBusinessExceptions(ex);

        assertNotNull(result);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        assertEquals("CODE0", result.getBody().getError().getErrorCode());
        assertEquals("Business exception message", result.getBody().getError().getErrorMessage());
    }

    @Test
    public void test_handleConnectionExceptions_success() {
        SQLException sqlEx = new SQLException("SQL exception message");
        SQLGrammarException sqlGrammarEx = new SQLGrammarException("SQL grammar exception message", sqlEx);

        ResponseEntity<BaseResponse> result = this.exceptionHandler.handleConnectionExceptions(sqlGrammarEx);

        assertNotNull(result);
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, result.getStatusCode());
        assertEquals("SUN001", result.getBody().getError().getErrorCode());
        assertEquals("SQL grammar exception message", result.getBody().getError().getErrorMessage());
    }
}