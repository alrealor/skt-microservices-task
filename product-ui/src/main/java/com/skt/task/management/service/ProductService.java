package com.skt.task.management.service;

import com.skt.task.common.config.MQConfig;
import com.skt.task.common.domain.ProductDTO;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Service class containing business logic of product controller
 */
@Service
public class ProductService {

    private RabbitTemplate rabbitTemplate;

    private DirectExchange directExchange;

    public ProductService(RabbitTemplate rabbitTemplate, DirectExchange directExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.directExchange = directExchange;
    }

    /**
     * Send a get request to the product rpc queue in order to respond in a sync way and display list of products
     *
     * @return a collection of {@link List<ProductDTO>} type
     */
    public List<ProductDTO> sendGetMsg() throws Exception {
        return new ArrayList<>((Collection<ProductDTO>) rabbitTemplate
                .convertSendAndReceive(directExchange.getName(), MQConfig.PRODUCT_RPC_GET_RK, "TEST"));
    }

    /**
     * method to publish message into post queue in order to create a product in an async way
     *
     * @param product product to be created of {@link ProductDTO} type
     */
    public void publishPostRequestMsg(ProductDTO product) throws Exception {
        this.rabbitTemplate.convertAndSend(MQConfig.PRODUCT_POST_EX, MQConfig.PRODUCT_POST_RK, product);
    }
}
