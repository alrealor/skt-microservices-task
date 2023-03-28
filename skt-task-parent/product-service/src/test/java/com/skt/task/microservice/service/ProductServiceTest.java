package com.skt.task.microservice.service;

import com.skt.task.common.domain.ProductDTO;
import com.skt.task.microservice.dao.entity.Product;
import com.skt.task.microservice.dao.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * test class for products service from product microservice
 */
@RunWith(SpringRunner.class)
public class ProductServiceTest {

    @MockBean
    private ProductRepository productRepository;

    private ProductService productService;

    /**
     * setup before test executions used for init a product controller
     */
    @Before
    public void setUp() throws Exception {
        this.productService = new ProductService(productRepository);
    }

    /**
     * test method for getting products from service layer
     */
    @Test
    public void test_getProducts_success() throws Exception {

        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product 1");
        product.setPrice(new BigDecimal(100.50));

        List<Product> products = singletonList(product);

        doReturn(products)
                .when(productRepository)
                .getProducts();

        List<ProductDTO> result = this.productService.getProducts();

        assertNotNull(result);
        assertEquals(products.get(0).getId(), result.get(0).getId());
        assertEquals(products.get(0).getName(), result.get(0).getName());
        assertEquals(products.get(0).getPrice(), result.get(0).getPrice());
    }


    /**
     * test method for getting products from service layer
     */
    @Test
    public void test_getProducts_success2() throws Exception {

        Product product = new Product(1L, "Test Product", new BigDecimal(100.50));

        List<Product> products = singletonList(product);

        doReturn(products)
                .when(productRepository)
                .getProducts();

        List<ProductDTO> result = this.productService.getProducts();

        assertNotNull(result);
        assertEquals(products.get(0).getId(), result.get(0).getId());
        assertEquals(products.get(0).getName(), result.get(0).getName());
        assertEquals(products.get(0).getPrice(), result.get(0).getPrice());
    }

    /**
     * test method for creating product from service layer
     */
    @Test
    public void test_addProduct_success() throws Exception {

        ProductDTO reqProduct = getTestProductDTO();

        doReturn(1L)
                .when(this.productRepository)
                .addProduct(any(String.class), any(BigDecimal.class));

        ProductDTO product = this.productService.addProduct(reqProduct);

        assertNotNull(product);
        assertEquals(reqProduct.getId(), product.getId());
        assertEquals(reqProduct.getName(), product.getName());
        assertEquals(reqProduct.getPrice(), product.getPrice());
        verify(this.productRepository, times(1)).
                addProduct(any(String.class), any(BigDecimal.class));
    }

    /**
     * test method for creating product from service layer
     */
    @Test(expected = Exception.class)
    public void test_addProduct_throw_businessException() throws Exception {

        ProductDTO reqProduct = getTestProductDTO();

        doReturn(null)
                .when(this.productRepository)
                .addProduct(any(String.class), any(BigDecimal.class));

        this.productService.addProduct(reqProduct);
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