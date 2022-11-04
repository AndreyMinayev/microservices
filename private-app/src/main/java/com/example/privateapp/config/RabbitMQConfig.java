package com.example.privateapp.config;



import com.example.privateapp.services.Consumer;
import com.example.shared.enums.CardOperation;
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

	@Value("${queue.name}")
	String queue;


    @Value("${spring.rabbitmq.username}")
    String username;

	@Value("${cards.rabbitmq.exchange}")
	String exchange;

	@Value("${spring.rabbitmq.password}")
    private String password;

	@Bean
	public DirectExchange directExchange() {
		return new DirectExchange(exchange);
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange(exchange);
	}

	@Bean
	Queue getQueue() {
		return new Queue(queue, false);
	}

	@Bean
	Binding declareBindingCreateCard() {
		return BindingBuilder.bind(getQueue()).to( exchange()).with(CardOperation.CREATE.getId());
	}

	@Bean
	Binding declareBindingDeleteCard() {
		return BindingBuilder.bind(getQueue()).to( exchange()).with(CardOperation.DELETE.getId());
	}

	@Bean
	Binding declareBindingListCard() {
		return BindingBuilder.bind(getQueue()).to( exchange()).with(CardOperation.LIST.getId());
	}

	@Bean
	Binding declareBindingUpdateCard() {
		return BindingBuilder.bind(getQueue()).to( exchange()).with(CardOperation.UPDATE.getId());
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
