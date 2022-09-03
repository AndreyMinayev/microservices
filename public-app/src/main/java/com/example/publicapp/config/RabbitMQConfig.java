package com.example.publicapp.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${cards.rabbitmq.newCardQueue}")
    String newCardQueue;

    @Value("${cards.rabbitmq.requestQueue}")
    String requestQueue;

    @Value("${cards.rabbitmq.deleteQueue}")
    String deleteQueue;

    @Value("${cards.rabbitmq.exchange}")
    String exchange;

    @Value("${cards.rabbitmq.newCardRoute}")
    private String newCardRoutingKey;
    @Value("${cards.rabbitmq.requestRoute}")
    private String requestRoutingKey;
    @Value("${cards.rabbitmq.deleteRoute}")
    private String deleteRoutingKey;


    @Bean
    Queue getNewCardQueue() {
        return new Queue(newCardQueue, false);
    }

    @Bean
    Queue getRequestQueue() {
        return new Queue(requestQueue, false);
    }

    @Bean
    Queue getDeleteQueue() {
        return new Queue(deleteQueue, false);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    Binding declareBindingNewCard() {
        return BindingBuilder.bind(getNewCardQueue()).to( exchange()).with(newCardRoutingKey);
    }

    @Bean
    Binding declareBindingDelete() {
        return BindingBuilder.bind(getDeleteQueue()).to(exchange()).with(deleteRoutingKey);
    }

    @Bean
    Binding declareBindingRequest() {
        return BindingBuilder.bind(getRequestQueue()).to(exchange()).with(requestRoutingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

}
