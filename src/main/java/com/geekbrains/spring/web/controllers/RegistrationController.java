package com.geekbrains.spring.web.controllers;

import com.geekbrains.spring.web.converters.UserConventer;
import com.geekbrains.spring.web.dto.UserDto;
import com.geekbrains.spring.web.entities.Product;
import com.geekbrains.spring.web.entities.Role;
import com.geekbrains.spring.web.entities.User;
import com.geekbrains.spring.web.services.UserService;
import com.geekbrains.spring.web.validators.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reg")
@Slf4j
public class RegistrationController {

    private final UserService userService;
    private final UserValidator userValidator;
    private final UserConventer userConventer;

    @PostMapping
    public UserDto registerUser(@RequestBody UserDto userDto) {
        log.info("Registering user: {}", userDto);
        userValidator.validate(userDto);
        User user = userConventer.dtoToEntity(userDto);
        return userConventer.entityToDto(userService.registerUser(user));
    }
}
