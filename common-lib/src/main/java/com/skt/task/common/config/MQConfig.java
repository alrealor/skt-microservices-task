package com.skt.task.common.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for handling connection with RabbitMQ queues producer/consumer
 */
@Configuration
public class MQConfig {

    public static final String PRODUCT_RPC_GET_QUEUE = "product_rpc_get_queue";
    public static final String PRODUCT_RPC_GET_EX= "product_rpc_get_exchange" ;
    public static final String PRODUCT_RPC_GET_RK = "product_rpc_get_routingkey";

    public static final String PRODUCT_POST_QUEUE = "product_post_queue";
    public static final String PRODUCT_POST_EX = "product_post_exchange" ;
    public static final String PRODUCT_POST_RK = "product_post_routingkey";

    // TODO - inject properties -> @Value("${rmq.product.queue}")

    /**
     * Method to create product request queue
     * @return queue of {@link Queue} type
     */
    @Bean(name="getRpcQueue")
    public Queue getRpcQueue() {
        return new Queue(PRODUCT_RPC_GET_QUEUE);
    }

    /**
     * Method to create product response queue
     * @return queue of {@link Queue} type
     */
    @Bean(name = "postQueue")
    public Queue postQueue() {
        return new Queue(PRODUCT_POST_QUEUE);
    }

    /**
     * Method to create TopicExchange
     * @return topic exchange of {@link TopicExchange} type
     */
    @Bean(name = "getDirectExchange")
    public DirectExchange getDirectExchange() {
        return new DirectExchange(PRODUCT_RPC_GET_EX);
    }

    @Bean(name = "postTopicExchange")
    public TopicExchange postTopicExchange() {
        return new TopicExchange(PRODUCT_POST_EX);
    }

    /**
     * Method to bind get rpc queue and direct exchange
     * @return binding builder of {@link Binding} type
     */
    @Bean
    public Binding getRpcBinding(DirectExchange getDirectExchange, Queue getRpcQueue) {
        return BindingBuilder
                .bind(getRpcQueue)
                .to(getDirectExchange)
                .with(PRODUCT_RPC_GET_RK);
    }

    /**
     * Method to bind queue and topic exchange using a routing key
     * @return binding builder of {@link Binding} type
     */
    @Bean
    public Binding postBinding(Queue postQueue, TopicExchange postTopicExchange) {
        return BindingBuilder
                .bind(postQueue)
                .to(postTopicExchange)
                .with(PRODUCT_POST_RK);
    }

    /**
     * Method to create converter from json to object and vice-versa
     * @return message converter of {@link MessageConverter} type
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * Method to create the template client
     * @return template client of {@link AmqpTemplate} type
     */
    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}