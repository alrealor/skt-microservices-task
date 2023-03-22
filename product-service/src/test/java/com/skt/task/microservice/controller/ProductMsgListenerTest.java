package com.skt.task.microservice.controller;

import com.skt.task.common.domain.ProductDTO;
import com.skt.task.microservice.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProductMsgListenerTest {

    @MockBean
    private ProductService productService;

    private ProductMsgListener productMsgListener;

    @Before
    public void setUp() throws Exception {
        productMsgListener = new ProductMsgListener(productService);
    }

    @Test
    public void test_getProductMessage_success() throws Exception {

        List<ProductDTO> products = singletonList(getTestProductDTO());

        doReturn(products)
                .when(productService)
                .getProducts();

        List<ProductDTO> result = productMsgListener.getProductMessage("TEST");

        assertNotNull(result);
        assertEquals(products.get(0).getId(), result.get(0).getId());
        assertEquals(products.get(0).getName(), result.get(0).getName());
        assertEquals(products.get(0).getPrice(), result.get(0).getPrice());
        verify(productService, times(1)).getProducts();
    }

    @Test
    public void postProductMessage() throws Exception {

        ProductDTO product = getTestProductDTO();

        doReturn(product)
                .when(productService)
                .addProduct(product);

        productMsgListener.postProductMessage(product);

        verify(productService, times(1)).addProduct(any(ProductDTO.class));
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