package com.skt.task.management.service;

import com.skt.task.common.config.MQConfig;
import com.skt.task.common.domain.ProductDTO;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Service class containing business logic of product controller
 */
@Service
public class ProductService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange directExchange;

//    public ProductService(RabbitTemplate rabbitTemplate) {
//        this.rabbitTemplate = rabbitTemplate;
//    }

    /**
     * Send a get request to the product rpc queue in order to respond and display list of products
     *
     * @return a collection of {@link List<ProductDTO>} type
     */
    public List<ProductDTO> sendGet() {
        return new ArrayList<>((Collection<ProductDTO>) rabbitTemplate
                .convertSendAndReceive(directExchange.getName(), MQConfig.PRODUCT_RPC_GET_RK, "TEST"));
    }

    /**
     * method to publish message into post queue in order to create a product
     *
     * @param product product to be created of {@link ProductDTO} type
     */
    public void publishPostRequestMsg(ProductDTO product) {
        this.rabbitTemplate.convertAndSend(MQConfig.PRODUCT_POST_EX, MQConfig.PRODUCT_POST_RK, product);
    }
}
