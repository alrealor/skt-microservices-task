package com.skt.task.management.service;

import com.skt.task.common.config.MQConfig;
import com.skt.task.common.domain.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.skt.task.common.config.MQConfig.BEAN_GET_DIRECT_EXCHANGE;

/**
 * Service class containing business logic of product controller
 */
@Service
@Slf4j
public class ProductService {

    private RabbitTemplate rabbitTemplate;

    private DirectExchange directExchange;

    public ProductService(RabbitTemplate rabbitTemplate,
                          @Qualifier(BEAN_GET_DIRECT_EXCHANGE) DirectExchange directExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.directExchange = directExchange;
    }

    /**
     * Send a get request to the product rpc queue in order to respond in a sync way and display list of products
     *
     * @return a collection of {@link List<ProductDTO>} type
     */
    public List<ProductDTO> sendGetMsg() throws Exception {
        List<ProductDTO> products = new ArrayList<>((Collection<ProductDTO>) rabbitTemplate
                .convertSendAndReceive(directExchange.getName(), MQConfig.GET_RPC_ROUTING_KEY, "GET_PRODUCTS_REQUEST"));
        log.debug("RPC to get list of products was executed successfully");
        return products;
    }

    /**
     * method to publish message into post queue in order to create a product in an async way
     *
     * @param product product to be created of {@link ProductDTO} type
     */
    public void publishPostRequestMsg(ProductDTO product) {
        this.rabbitTemplate.convertAndSend(MQConfig.POST_EXCHANGE, MQConfig.POST_ROUTING_KEY, product);
        log.debug("Message to create a new product was sent successfully: " + product.toString());
    }
}
