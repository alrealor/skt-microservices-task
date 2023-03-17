package com.skt.task.microservice.controller;

import com.skt.task.common.domain.ProductDTO;
import com.skt.task.common.domain.message.ProductRequestGet;
import com.skt.task.common.domain.message.ProductResponseGet;
import com.skt.task.microservice.config.MQConfig;
import com.skt.task.microservice.service.ProductService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Listener class for getting the available messages coming from product-queue queue
 */
@Component
public class ProductMsgListener {

    private final ProductService productService;

    private final ProductMsgPublisher productMsgPublisher;

    /**
     * Contructor used to dependency injection
     *
     * @param productService {@link ProductService}
     */
    public ProductMsgListener(ProductService productService, ProductMsgPublisher productMsgPublisher) {
        this.productService = productService;
        this.productMsgPublisher = productMsgPublisher;
    }

    /**
     * method to retrieve the request message from product-queue queue,
     * then make the query of products from DB and publish the results into product-queue
     *
     * @param message queue message of {@link ProductDTO} type
     */
    @RabbitListener(queues = MQConfig.PRODUCT_QUEUE)
    public void getProductMessage(ProductRequestGet requestMsg) throws Exception {
        List<ProductDTO> products =  productService.getProducts();
        ProductResponseGet responseMsg = new ProductResponseGet(products);
        productMsgPublisher.publishProductsMsg(responseMsg);
    }


    /**
     * method to retrieve the request message from product-queue queue,
     * then make the query of products from DB and publish the results into product-queue
     *
     * @param message queue message of {@link ProductDTO} type
     */
    @RabbitListener(queues = MQConfig.PRODUCT_QUEUE)
    public void insertProductMessage(ProductDTO message) throws Exception {
        ProductDTO product =  productService.addProduct(message);
        productMsgPublisher.publishProductMsg(product);
    }
}
