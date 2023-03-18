package com.skt.task.microservice.controller;


import com.skt.task.common.config.MQConfig;
import com.skt.task.common.domain.ProductDTO;
import com.skt.task.common.domain.message.ProductPostRequest;
import com.skt.task.microservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;


/**
 * Listener class for getting the available messages coming from product-queue queue
 */
@Component

public class ProductMsgListener {

    @Autowired
    RabbitTemplate rabbitTemplate;

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
     * method to retrieve the request message from product_post_queue queue
     * and execute service operation to create a product
     *
     * @param message queue message of {@link ProductDTO} type
     */
    @RabbitListener(queues = MQConfig.PRODUCT_POST_QUEUE)
    public void getProductMessage(ProductPostRequest message) throws Exception {
        this.productService.addProduct(message.getProduct());
    }

    /**
     * method to retrieve the request message from product-queue queue,
     * then make the query of products from DB and publish the results into product-queue
     *
     * @param message queue message of {@link ProductDTO} type
     */
//    @RabbitListener(queues = MQConfiguration.PRODUCT_REQ_QUEUE)
//    public void getProductMessage(ProductGetMessage message) throws Exception {
//        switch(message.getMessageType()) {
//            case "POST_REQUEST":
//                this.productService.addProduct(message.getProduct());
//                break;
//            default:
//                this.productMsgPublisher.publishGetResponseMsg(this.productService.getProducts());
//                break;
//        }
//    }

//    @RabbitListener(queues = RabbitMQConfig.PRODUCT_RPC_GET_QUEUE)
//    public void process(Message message) {
//        byte[] body = message.getBody();
//        String out = new String(body, StandardCharsets.UTF_8);
//        System.out.println("out: " + out);
//        //This is the message to be returned by the server
//        Message build = MessageBuilder.withBody(("I am the server, I received the message from the client：" + new String(body)).getBytes()).build();
//        CorrelationData correlationData = new CorrelationData(message.getMessageProperties().getCorrelationIdString());
//        System.out.println("correlation id: " + correlationData.getId());
//        rabbitTemplate.sendAndReceive(RabbitMQConfig.PRODUCT_RPC_GET_EX,
//                RabbitMQConfig.PRODUCT_RPC_GET_REPLY_QUEUE,
//                build,
//                correlationData);
//    }
}
