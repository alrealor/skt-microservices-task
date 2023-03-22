package com.skt.task.management.service;

import com.skt.task.common.domain.ProductDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class ProductServiceTest {

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private DirectExchange directExchange;

    private ProductService productService;

    /**
     * setup before test executions used for init a product controller
     */
    @Before
    public void setUp() throws Exception {
        this.productService = new ProductService(rabbitTemplate, directExchange);
    }

    /**
     * test method for publishing a get message into RPC queue in order to get available products
     */
    @Test
    public void test_sendGet_success() throws Exception {

        doReturn(singletonList(getTestProductDTO()))
                .when(rabbitTemplate)
                .convertSendAndReceive(any(String.class), any(String.class), any(String.class));

        List<ProductDTO> result = this.productService.sendGetMsg();

        assertNotNull(result);
        assertEquals(singletonList(getTestProductDTO()), result);
    }

    /**
     * test method for publishing a post message into post(publisher/subscriber) queue in order to create a new product
     */
    @Test
    public void test_publishPostRequestMsg_success() throws Exception {

        doNothing()
                .when(this.rabbitTemplate)
                .convertAndSend(any(String.class), any(String.class), any(ProductDTO.class));

        this.productService.publishPostRequestMsg(getTestProductDTO());

        verify(this.rabbitTemplate, times(1)).
                convertAndSend(any(String.class), any(String.class), any(ProductDTO.class));
    }

    /**
     * get a dummy product DTO
     *
     * @return a product of {@link ProductDTO} type
     */
    private ProductDTO getTestProductDTO() {
        return new ProductDTO(1L, "Test Product 1", new BigDecimal("100.50"));
    }
}