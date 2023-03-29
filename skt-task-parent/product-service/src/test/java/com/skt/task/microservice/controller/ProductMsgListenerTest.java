package com.skt.task.microservice.controller;

import com.skt.task.common.domain.ProductDTO;
import com.skt.task.common.exception.IncorrectMessageException;
import com.skt.task.microservice.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Test class for product message listener controller
 */

@RunWith(SpringJUnit4ClassRunner.class)
public class ProductMsgListenerTest {

    @MockBean
    private ProductService productService;

    private ProductMsgListener productMsgListener;

    /**
     * setup class
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        productMsgListener = new ProductMsgListener(productService);
    }

    /**
     * test a success execution of method getProductMessage
     *
     * @throws Exception
     */
    @Test
    public void test_getProductMessage_success() throws Exception {

        List<ProductDTO> products = singletonList(getTestProductDTO());

        doReturn(products)
                .when(productService)
                .getProducts();

        List<ProductDTO> result = productMsgListener.getProductMessage("GET_PRODUCT_LIST");

        assertNotNull(result);
        assertEquals(products.get(0).getName(), result.get(0).getName());
        assertEquals(products.get(0).getPrice(), result.get(0).getPrice());
        verify(productService, times(1)).getProducts();
    }

    /**
     * test a success execution of method addProduct
     *
     * @throws Exception
     */

    @Test
    public void test_postProductMessage_success() throws Exception {

        ProductDTO product = getTestProductDTO();

        doReturn(product)
                .when(productService)
                .addProduct(product);

        productMsgListener.postProductMessage(product);

        verify(productService, times(1)).addProduct(any(ProductDTO.class));
    }


    /**
     * test a fail execution of method postProductMessage due to negative product price
     *
     * @throws Exception
     */
    @Test(expected = IncorrectMessageException.class)
    public void test_postProductMessage_fail_negativePrice_incorrectMessageException() throws Exception {

        ProductDTO product = getTestProductDTO();
        product.setPrice(new BigDecimal("-50"));

        productMsgListener.postProductMessage(product);
    }

    /**
     * test a fail execution of method postProductMessage due to missing product name
     *
     * @throws Exception
     */
    @Test(expected = IncorrectMessageException.class)
    public void test_postProductMessage_fail_missingName_incorrectMessageException() throws Exception {

        ProductDTO product = getTestProductDTO();
        product.setName("");

        productMsgListener.postProductMessage(product);
    }

    /**
     * get a dummy product DTO
     *
     * @return a product of {@link ProductDTO} type
     */
    private ProductDTO getTestProductDTO() {
        return new ProductDTO("Test Product 1", new BigDecimal("100.50"));
    }
}