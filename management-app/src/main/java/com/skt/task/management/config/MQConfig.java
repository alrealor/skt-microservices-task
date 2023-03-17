package com.skt.task.management.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {
    private static final String PRODUCT_QUEUE = "product-queue";
    private static final String PRODUCT_EXCHANGE = "product_exchange";
    private static final String PRODUCT_ROUTING_KEY = "product_routingKey";

    /**
     * Method to create Queue
     * @return queue of {@link Queue} type
     */
    @Bean
    public Queue queue() {
        return new Queue(PRODUCT_QUEUE);
    }

    /**
     * Method to create TopicExchange
     * @return topic exchange of {@link TopicExchange} type
     */
    @Bean
    public TopicExchange topic() {
        return new TopicExchange(PRODUCT_EXCHANGE);
    }

    /**
     * Method to bind queue and topic exchange using a routing key
     * @return binding builder of {@link Binding} type
     */
    @Bean
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
