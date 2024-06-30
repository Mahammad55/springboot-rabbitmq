package com.example.springbootrabbitmq.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.example.springbootrabbitmq.enums.ExchangeEnum.USER_DLQ_EXCHANGE;
import static com.example.springbootrabbitmq.enums.ExchangeEnum.USER_QUEUE_EXCHANGE;
import static com.example.springbootrabbitmq.enums.QueueEnum.USER_DLQ;
import static com.example.springbootrabbitmq.enums.QueueEnum.USER_QUEUE;
import static com.example.springbootrabbitmq.enums.RoutingKeyEnum.USER_DLQ_KEY;
import static com.example.springbootrabbitmq.enums.RoutingKeyEnum.USER_QUEUE_KEY;

@Configuration
public class RabbitMqConfig {
    @Bean
    public DirectExchange userQueueExchange() {
        return new DirectExchange(USER_QUEUE_EXCHANGE.name());
    }

    @Bean
    public DirectExchange userDLQExchange() {
        return new DirectExchange(USER_DLQ_EXCHANGE.name());
    }

    @Bean
    public Queue userQueue() {
        return QueueBuilder.durable(USER_QUEUE.name())
                .withArgument("x-dead-letter-exchange", USER_DLQ_EXCHANGE.name())
                .withArgument("x-dead-letter-routing-key", USER_DLQ_KEY.name())
                .build();
    }

    @Bean
    public Queue userDLQ() {
        return QueueBuilder.durable(USER_DLQ.name()).build();
    }

    @Bean
    public Binding userQueueBinding() {
        return BindingBuilder.bind(userQueue())
                .to(userQueueExchange()).with(USER_QUEUE_KEY.name());
    }

    @Bean
    public Binding userDLQBinding() {
        return BindingBuilder.bind(userDLQ())
                .to(userDLQExchange()).with(USER_DLQ_KEY.name());
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
