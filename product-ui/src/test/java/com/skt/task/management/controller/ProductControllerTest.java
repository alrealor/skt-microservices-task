package com.skt.task.management.controller;

import com.skt.task.common.domain.ProductDTO;
import com.skt.task.management.service.ProductService;
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
import org.springframework.ui.Model;

import java.math.BigDecimal;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * test class for component of type {@link ProductController} of web layer
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private Model model;

    @MockBean
    private AmqpTemplate template;

    private ProductController productController;

    /**
     * setup before test executions used for init a product controller
     */
    @Before
    public void setup(){
        productController = new ProductController(productService);
    };

    /**
     * test method for getting the index page
     *
     * @throws Exception
     */
    @Test
    public void test_get_goToIndexPage_success() throws Exception {

        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andDo(print());
    }

    /**
     * test method for going from index to list of products page
     *
     * @throws Exception
     */
    @Test
    public void test_get_goToProductListPage_success() throws Exception {

        this.mockMvc.perform(get("/goToListPage"))
                .andExpect(status().isOk())
                .andExpect(view().name("list-products"))
                .andDo(print());
    }

    /**
     * test method for going from index to create prouct page
     *
     * @throws Exception
     */
    @Test
    public void test_post_goToCreateProductPage_success() throws Exception {

        this.mockMvc.perform(get("/goToCreatePage"))
                .andExpect(status().isOk())
                .andExpect(view().name("create-product"))
                .andDo(print());
    }

    /**
     * test method for create product page flow
     *
     * @throws Exception
     */
    @Test
    public void test_get_createProduct_success() throws Exception {

        this.mockMvc.perform(post("/createProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    /**
     * test list products method from controller
     *
     * @throws Exception
     */
    @Test
    public void test_listProducts_success() throws Exception {

        doReturn(singletonList(getTestProductDTO()))
                .when(productService)
                .sendGetMsg();

        String result = this.productController.listProducts(model);

        assertEquals("list-products", result);
    }

    /**
     * test create product method from controller
     *
     * @throws Exception
     */
    @Test
    public void test_createProduct_success() throws Exception {

        ProductDTO product = getTestProductDTO();

        doReturn(product)
                .when(productService)
                .publishPostRequestMsg(product);

        ResponseEntity<ProductDTO> result = this.productController.createProduct(product);

        assertNotNull(result);
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