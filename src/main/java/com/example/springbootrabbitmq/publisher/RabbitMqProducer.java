package com.example.springbootrabbitmq.publisher;

import com.example.springbootrabbitmq.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.example.springbootrabbitmq.enums.ExchangeEnum.USER_QUEUE_EXCHANGE;
import static com.example.springbootrabbitmq.enums.RoutingKeyEnum.USER_QUEUE_KEY;

@Service
@RequiredArgsConstructor
public class RabbitMqProducer {
    private final RabbitTemplate rabbitTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqProducer.class);

    public String sendUserDto(UserDto userDto) {
        rabbitTemplate.convertAndSend(USER_QUEUE_EXCHANGE.name(), USER_QUEUE_KEY.name(), userDto);
        LOGGER.info("User dto has been successfully send :{}", userDto);
        return "OK";
    }
}
