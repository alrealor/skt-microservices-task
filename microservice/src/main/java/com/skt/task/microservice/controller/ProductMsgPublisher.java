package com.skt.task.microservice.controller;

import com.skt.task.common.domain.ProductDTO;
import com.skt.task.common.domain.message.ProductResponseGet;
import com.skt.task.microservice.config.MQConfig;
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
    public String publishProductsMsg(final ProductResponseGet responseMsg) {
        rabbitTemplate.convertAndSend(MQConfig.PRODUCT_EXCHANGE, MQConfig.PRODUCT_ROUTING_KEY, responseMsg);
        return SUCCESS_MESSAGE;
    }

    /**
     * method to retrieve the messages into product-queue queue
     * @param product - product message of {@link ProductDTO} type
     */
    public String publishProductMsg(final ProductDTO product) {
        rabbitTemplate.convertAndSend(MQConfig.PRODUCT_EXCHANGE, MQConfig.PRODUCT_ROUTING_KEY, product);
        return SUCCESS_MESSAGE;
    }
}
