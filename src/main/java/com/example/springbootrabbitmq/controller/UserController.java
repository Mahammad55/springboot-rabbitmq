package com.example.springbootrabbitmq.controller;

import com.example.springbootrabbitmq.dto.UserDto;
import com.example.springbootrabbitmq.publisher.RabbitMqProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final RabbitMqProducer rabbitMQProducer;

    @PostMapping
    public ResponseEntity<String> sendUserDto(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(rabbitMQProducer.sendUserDto(userDto));
    }
}

