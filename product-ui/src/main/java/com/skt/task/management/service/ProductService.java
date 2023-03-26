package com.skt.task.management.service;

import com.skt.task.common.config.MQConfig;
import com.skt.task.common.domain.ProductDTO;
import com.skt.task.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Service class containing business logic of product controller
 */
@Service
@Slf4j
public class ProductService {

    private RabbitTemplate rabbitTemplate;

    private DirectExchange directExchange;

    public ProductService(RabbitTemplate rabbitTemplate,
                          @Qualifier("getDirectExchange") DirectExchange directExchange) {
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
                .convertSendAndReceive(directExchange.getName(), MQConfig.GET_RPC_ROUTING_KEY, "TEST"));
    }

    /**
     * method to publish message into post queue in order to create a product in an async way
     *
     * @param product product to be created of {@link ProductDTO} type
     */
    public ProductDTO publishPostRequestMsg(ProductDTO product) throws Exception {
        ProductDTO result = (ProductDTO) this.rabbitTemplate
                .convertSendAndReceive(MQConfig.POST_EXCHANGE, MQConfig.POST_ROUTING_KEY, product);

        if (result == null) {
            log.error("Error creating a new product for message: " + product.toString());
            throw new BusinessException("CODE700-Product could not be created");
        }

        log.info("POST message sent successfully with return: " + result);
        return result;
    }
}
