package com.example.privateapp.config;



import com.example.privateapp.services.Consumer;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
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

    @Value("${spring.rabbitmq.username}")
    String username;

	@Value("${cards.rabbitmq.exchange}")
	String exchange;

	@Value("${spring.rabbitmq.password}")
    private String password;

	@Value("${cards.rabbitmq.newCardRoute}")
	private String newCardRoutingKey;
	@Value("${cards.rabbitmq.requestRoute}")
	private String requestRoutingKey;
	@Value("${cards.rabbitmq.deleteRoute}")
	private String deleteRoutingKey;
	@Bean
	public DirectExchange directExchange() {
		return new DirectExchange(exchange);
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange(exchange);
	}

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
	public MessageConverter jackson2MessageConverter() {
		return new Jackson2JsonMessageConverter();
	}



	@Bean
	public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setMessageConverter(jackson2MessageConverter());
		return factory;
	}
}
