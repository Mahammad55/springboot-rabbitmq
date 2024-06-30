package com.example.springbootrabbitmq.consumer;

import com.example.springbootrabbitmq.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);

    @RabbitListener(queues = "#{T(com.example.springbootrabbitmq.enums.QueueEnum).USER_QUEUE.name()}")
    public void consumerJson(UserDto userDto) {
        LOGGER.info("Received data is :{}", userDto);
    }
}
