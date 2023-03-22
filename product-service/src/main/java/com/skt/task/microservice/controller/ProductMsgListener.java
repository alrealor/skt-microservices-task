package com.skt.task.microservice.controller;


import com.skt.task.common.config.MQConfig;
import com.skt.task.common.domain.ProductDTO;
import com.skt.task.microservice.service.ProductService;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.util.List;

/**
 * Listener class for getting the available messages coming from product-queue queue
 */
@Component
public class ProductMsgListener {

    private final ProductService productService;

    /**
     * Constructor used to dependency injection
     *
     * @param productService {@link ProductService}
     */
    public ProductMsgListener(ProductService productService) {
        this.productService = productService;
    }

    /**
     * method to retrieve the request message from product_rpc_get_queue queue,
     * then make the query of products from DB and return resutl of rpc call
     *
     * @param message of list of products of {@link ProductDTO} type
     */
    @RabbitListener(queues = MQConfig.PRODUCT_RPC_GET_QUEUE)
    public List<ProductDTO> getProductMessage(String message) throws Exception {
        return productService.getProducts();
    }

    /**
     * method to retrieve the request message from product_post_queue queue
     * and execute service operation to create a new product
     *
     * @param message queue message of {@link ProductDTO} type
     */
    @RabbitListener(queues = MQConfig.PRODUCT_POST_QUEUE)
    public void postProductMessage(ProductDTO message) throws Exception {
        this.productService.addProduct(message);
    }
}
