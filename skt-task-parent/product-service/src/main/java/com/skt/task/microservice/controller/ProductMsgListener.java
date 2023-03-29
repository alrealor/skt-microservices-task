package com.skt.task.microservice.controller;


import com.skt.task.common.config.MQConfig;
import com.skt.task.common.domain.ProductDTO;
import com.skt.task.common.exception.BusinessException;
import com.skt.task.common.exception.IncorrectMessageException;
import com.skt.task.microservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

import static com.skt.task.common.constants.ErrorCodes.INVALID_PRODUCT_NAME;
import static com.skt.task.common.constants.ErrorCodes.INVALID_PRODUCT_PRICE;

/**
 * Listener class for getting the available messages coming from product-queue queue
 */
@Component
@Slf4j
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
    @RabbitListener(queues = MQConfig.GET_RPC_QUEUE)
    public List<ProductDTO> getProductMessage(String message) {
        return productService.getProducts();
    }

    /**
     * method to retrieve the request message from product_post_queue queue
     * and execute service operation to create a new product
     *
     * @param message queue message of {@link ProductDTO} type
     */
    @RabbitListener(queues = MQConfig.POST_QUEUE)
    public void postProductMessage(ProductDTO message) throws BusinessException {
        log.debug("Message to create product is received: " + message.toString());

        // product name validation
        if (StringUtils.isEmpty(message.getName())){
            log.error("Product name is blank");
            throw new IncorrectMessageException(INVALID_PRODUCT_NAME, "Product name is blank");
        }
        // price validation
        if (message.getPrice().compareTo(BigDecimal.ZERO) <= 0){
            log.error("Product price is equals or lower than zero");
            throw new IncorrectMessageException(INVALID_PRODUCT_PRICE, "Product price is equals or lower than zero");
        }

        // Sanitize name param
        message.setName(StringEscapeUtils.escapeHtml4(message.getName()));

        this.productService.addProduct(message);
        log.debug("Product was added successfully");
    }
}
