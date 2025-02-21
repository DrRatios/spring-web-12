package com.aleksgolds.spring.web.auth.controllers;

import com.aleksgolds.spring.web.auth.conventers.UserConventer;
import com.aleksgolds.spring.web.auth.dto.UserDto;
import com.aleksgolds.spring.web.auth.entities.User;
import com.aleksgolds.spring.web.auth.services.UserService;
import com.aleksgolds.spring.web.auth.validators.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
