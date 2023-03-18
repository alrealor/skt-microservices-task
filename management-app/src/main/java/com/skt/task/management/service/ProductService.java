package com.skt.task.management.service;

import com.skt.task.common.config.MQConfig;
import com.skt.task.common.domain.ProductDTO;
import com.skt.task.common.domain.message.ProductPostRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * Service class containing business logic of product controller
 */
@Service
public class ProductService {

    private RabbitTemplate rabbitTemplate;

    public ProductService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * method to publish message into post queue in order to create a product
     *
     * @param product product to be created of {@link ProductDTO} type
     */
    public void publishPostRequestMsg(ProductDTO product) {
        ProductPostRequest message = new ProductPostRequest();
        message.setProduct(product);
        this.rabbitTemplate.convertAndSend(MQConfig.PRODUCT_POST_EX, MQConfig.PRODUCT_POST_RK, message);
    }


//    public String pocRcpPublish(){
//        String message = "Hola";
//        // Create a message subject
//        Message newMessage = MessageBuilder.withBody(message.getBytes()).build();
//        newMessage.getMessageProperties().setCorrelationIdString("100");
//        //The customer sends a message
//        Message result = rabbitTemplate.sendAndReceive(RabbitMQConf.PRODUCT_RPC_GET_EX,
//                RabbitMQConf.PRODUCT_RPC_GET_QUEUE,
//                newMessage);
//        String response = "";
//        System.out.println("Result: " +result);
//        if (result != null) {
//            // To get message sent correlationId
//            String correlationId = newMessage.getMessageProperties().getCorrelationIdString();
//
//            // Get response header information
//            HashMap<String, Object> headers = (HashMap<String, Object>) result.getMessageProperties().getHeaders();
//            // Access server Message returned id
//            String msgId = (String) headers.get("spring_returned_message_correlation");
//            if (msgId.equals(correlationId)) {
//                response = new String(result.getBody());
//            }
//        }
//        return response;
//    }

//    public String pocRcpPublish2(){
//        String message = "Hola";
//        // Create a message subject
//        Message newMessage = MessageBuilder.withBody(message.getBytes()).build();
//        //The customer sends a message
//        String result = (String) rabbitTemplate.convertSendAndReceive(RabbitMQConf.PRODUCT_RPC_GET_QUEUE,message);
//        System.out.println("Result: " +result);
//        return result;
//    }
}
