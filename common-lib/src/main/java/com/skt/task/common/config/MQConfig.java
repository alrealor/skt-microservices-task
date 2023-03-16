package com.skt.task.common.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

/**
 * Configuration class for handling connection with RabbitMQ queues producer/consumer
 */
public class MQConfig {
    private static final String PRODUCT_QUEUE = "product-queue";
    private static final String PRODUCT_EXCHANGE = "product_exchange";
    private static final String PRODUCT_ROUTING_KEY = "product_routingKey";

    /**
     * Method to create Queue
     * @return queue of {@link Queue} type
     */
    public Queue queue() {
        return new Queue(PRODUCT_QUEUE);
    }

    /**
     * Method to create TopicExchange
     * @return topic exchange of {@link TopicExchange} type
     */
    public TopicExchange topic() {
        return new TopicExchange(PRODUCT_EXCHANGE);
    }

    /**
     * Method to bind queue and topic exchange using a routing key
     * @return binding builder of {@link Binding} type
     */
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(PRODUCT_ROUTING_KEY);
    }

    /**
     * Method to create converter from json to object and vice-versa
     * @return message converter of {@link MessageConverter} type
     */
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * Method to create the template client
     * @return template client of {@link AmqpTemplate} type
     */
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}