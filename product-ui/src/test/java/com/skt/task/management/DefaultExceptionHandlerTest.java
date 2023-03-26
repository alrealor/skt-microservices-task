package com.skt.task.management;

import com.skt.task.common.domain.BaseResponse;
import com.skt.task.common.exception.BusinessException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test of default eception handler class
 */
public class DefaultExceptionHandlerTest {

    private DefaultExceptionHandler exceptionHandler;

    @Before
    public void setup() {
        this.exceptionHandler = new DefaultExceptionHandler();
    }

    @Test
    public void test_handleBusinessExceptions_success() {

        BusinessException businesEx = new BusinessException("CODETEST-Error Message Description");

        ResponseEntity<BaseResponse> result = this.exceptionHandler.handleBusinessExceptions(businesEx);

        assertNotNull(result);
        assertEquals("CODETEST", result.getBody().getError().getErrorCode());
        assertEquals("Error Message Description", result.getBody().getError().getErrorMessage());
    }
}