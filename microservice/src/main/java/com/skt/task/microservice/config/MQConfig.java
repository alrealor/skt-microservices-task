package com.skt.task.microservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {
    public static final String PRODUCT_QUEUE = "product-queue";
    public static final String PRODUCT_EXCHANGE = "product_exchange" ;
    public static final String PRODUCT_ROUTING_KEY = "product_routingkey";
    //    @Value("${rmq.email.queue}")
    private String emailQueue;

//    @Value("${rmq.email.exchange}")
    private String emailExchange;

//    @Value("${rmq.email.routing-key}")
    private String emailRoutingKey;

    @Bean
    public Queue queue() {
        return new Queue(PRODUCT_QUEUE);
    }

    @Bean
    public TopicExchange topic() {
        return new TopicExchange(PRODUCT_EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(PRODUCT_ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
