package com.skt.task.microservice.controller;

import com.skt.task.common.domain.ProductDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Listener class for getting the available messages coming from product-queue queue
 */
@Component
public class ProductMsgPublisher {

    private final String SUCCESS_MESSAGE = "Message published successfully!";

    private RabbitTemplate rabbitTemplate;

    /**
     * Constructor to handle dependency injection
     * @param rabbitTemplate of {@link RabbitTemplate} type
     */
    public ProductMsgPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * method to retrieve the messages into product-queue queue
     * @param products - list of products message of {@link List} type
     */
//    public String publishGetResponseMsg(final List<ProductDTO> products) throws Exception {
//        ProductResponseMessage message = new ProductResponseMessage();
//        message.setMessageType("GET_RESPONSE");
//        message.setProducts(products);
//        rabbitTemplate.convertAndSend(MQConfiguration.PRODUCT_RES_EXCHANGE, MQConfiguration.PRODUCT_RES_ROUTING_KEY, message);
//        return SUCCESS_MESSAGE;
//    }

    /**
     * method to retrieve the messages into product-queue queue
     * @param product - product message of {@link ProductDTO} type
     */
//    public String publishPostResponseMsg(final ProductDTO product) throws Exception {
//        ProductResponseMessage message = new ProductResponseMessage();
//        message.setMessageType("POST_RESPONSE");
//        message.setProducts(Collections.singletonList(product));
//        rabbitTemplate.convertAndSend(MQConfiguration.PRODUCT_RES_EXCHANGE, MQConfiguration.PRODUCT_RES_ROUTING_KEY, message);
//        return SUCCESS_MESSAGE;
//    }
}
