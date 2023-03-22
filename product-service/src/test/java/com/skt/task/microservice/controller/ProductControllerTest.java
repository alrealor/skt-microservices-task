package com.skt.task.microservice.controller;

import com.skt.task.common.domain.BaseResponse;
import com.skt.task.common.domain.ProductDTO;
import com.skt.task.microservice.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private AmqpTemplate template;

    private ProductController productController;

    @Before
    public void setUp() throws Exception {
        this.productController = new ProductController(productService);
    }

    @Test
    public void test_getProductsWebPath_success() throws Exception {
        this.mockMvc.perform(get("/products"))
                .andExpect(status().isOk());
    }

    @Test
    public void test_addProductWebPath_success() throws Exception {
        String requestBody = "{}";
        this.mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * test for getting available products
     *
     * @throws Exception
     */
    @Test
    public void test_getProducts_success() throws Exception {

        ProductDTO product = getTestProductDTO();

        doReturn(singletonList(product))
                .when(productService)
                .getProducts();

        ResponseEntity<BaseResponse<ProductDTO>> result = this.productController.getProducts();

        List<ProductDTO> products = new ArrayList<>((Collection<ProductDTO>) result.getBody().getPayload());

        assertNotNull(result);
        assertEquals(product, products.get(0));
        verify(productService, times(1)).getProducts();
    }

    /**
     * test for getting available products
     *
     * @throws Exception
     */
    @Test
    public void test_postProduct_success() throws Exception {

        ProductDTO reqProduct = getTestProductDTO();

        doReturn(reqProduct)
                .when(productService)
                .addProduct(reqProduct);

        ResponseEntity<BaseResponse<ProductDTO>> result = this.productController.addProduct(reqProduct);

        ProductDTO resProduct = result.getBody().getPayload();

        assertNotNull(result);
        assertEquals(reqProduct, resProduct);
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