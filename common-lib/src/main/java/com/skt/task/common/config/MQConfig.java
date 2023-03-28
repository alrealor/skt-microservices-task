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

    public static final String BEAN_GET_DIRECT_EXCHANGE = "getDirectExchange";
    public static final String BEAN_POST_TOPIC_EXCHANGE = "postTopicExchange";
    public static final String BEAN_POST_DLQ_DIRECT_EXCHANGE = "postDlqExchange";

    // GET PRC QUEUE
    public static final String GET_RPC_QUEUE = "get_rpc_queue";
    public static final String GET_RPC_EXCHANGE= "get_rpc_exchange" ;
    public static final String GET_RPC_ROUTING_KEY= "get_rpc_routingkey";

    // POST QUEUE
    public static final String POST_QUEUE = "post_queue";
    public static final String POST_EXCHANGE = "post_exchange" ;
    public static final String POST_ROUTING_KEY = "post_routingkey";
    // POST DEAD LETTER QUEUE
    public static final String POST_DLQ_QUEUE = "post_dead_letter_queue";
    public static final String POST_DLQ_EXCHANGE = "post_dead_letter_exchange" ;
    public static final String POST_DLQ_ROUTING_KEY = "post_dead_letter_routingkey";

    // TODO - inject properties -> @Value("${rmq.product.queue}")

    /**
     * Method to create DirectExchange for get request queue
     *
     * @return topic exchange of {@link DirectExchange} type
     */
    @Bean(name = BEAN_GET_DIRECT_EXCHANGE)
    public DirectExchange getDirectExchange() {
        return new DirectExchange(GET_RPC_EXCHANGE);
    }

    /**
     * Method to create TopicExchange for post request queue
     *
     * @return topic exchange of {@link TopicExchange} type
     */
    @Bean(name = BEAN_POST_TOPIC_EXCHANGE)
    public TopicExchange postTopicExchange() {
        return new TopicExchange(POST_EXCHANGE);
    }

    /**
     * Method to create DirectExchange as a dead letter queue exchange for post request queue
     *
     * @return topic exchange of {@link DirectExchange} type
     */
    @Bean(name = BEAN_POST_DLQ_DIRECT_EXCHANGE)
    public DirectExchange postDlqExchange() {
        return new DirectExchange(POST_DLQ_EXCHANGE);
    }

    /**
     * Method to create product request queue
     *
     * @return queue of {@link Queue} type
     */
    @Bean
    public Queue getRpcQueue() {
        return new Queue(GET_RPC_QUEUE);
    }

    /**
     * Method to create product response queue
     *
     * @return queue of {@link Queue} type
     */
    @Bean
    public Queue postQueue() {

        return QueueBuilder.durable(POST_QUEUE)
                .withArgument("x-dead-letter-exchange", MQConfig.POST_DLQ_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", MQConfig.POST_DLQ_ROUTING_KEY)
                .build();
    }

    /**
     * Method to create a dead letter queue
     *
     * @return queue of {@link Queue} type
     */
    @Bean
    public Queue postDlq() {

        return QueueBuilder.durable(POST_DLQ_QUEUE).build();
    }


    /**
     * Method to bind get rpc queue and direct exchange, using this direct exchange the call is going to wait until
     * listener responds
     *
     * @return binding builder of {@link Binding} type
     */
    @Bean
    public Binding getRpcBinding() {
        return BindingBuilder
                .bind(getRpcQueue())
                .to(getDirectExchange())
                .with(GET_RPC_ROUTING_KEY);
    }

    /**
     * Method to bind queue and topic exchange using a routing key
     *
     * @return binding builder of {@link Binding} type
     */
    @Bean
    public Binding postBinding() {
        return BindingBuilder
                .bind(postQueue())
                .to(postTopicExchange())
                .with(POST_ROUTING_KEY);
    }

    /**
     * Method to bind queue and topic exchange using a routing key
     *
     * @return binding builder of {@link Binding} type
     */
    @Bean
    public Binding postDlqBinding() {
        return BindingBuilder
                .bind(postDlq())
                .to(postDlqExchange())
                .with(POST_DLQ_ROUTING_KEY);
    }

    /**
     * Method to create converter from json to object and vice-versa
     *
     * @return message converter of {@link MessageConverter} type
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * Method to create the template client
     *
     * @return template client of {@link AmqpTemplate} type
     */
    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}