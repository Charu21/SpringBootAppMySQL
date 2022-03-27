package com.charusmita.slsmettle.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${rabbitmq.exchange}")
    String exchangeName;

    @Value("${rabbitmq.queue}")
    String itemQueue;

    @Value("${rabbitmq.routingkey}")
    String routingKey;

    @Bean
    Queue itemQueue() {
        return new Queue(itemQueue, false);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    Binding itemBinding(Queue itemQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(itemQueue).to(topicExchange).with(routingKey);
    }
}
