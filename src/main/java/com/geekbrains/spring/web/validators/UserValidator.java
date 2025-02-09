package com.geekbrains.spring.web.validators;

import com.geekbrains.spring.web.dto.ProductDto;
import com.geekbrains.spring.web.dto.UserDto;
import com.geekbrains.spring.web.exceptions.ValidationException;
import com.geekbrains.spring.web.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserValidator {
    private final UserService userService;

    public void validate(UserDto userDto) throws ValidationException {
        List<String> errors = new ArrayList<>();
        if (userDto.getEmail().isBlank()) {
            errors.add("Значение email не может быть пустым");
        }
        if (userService.findByEmail(userDto.getEmail()).isPresent()) {
            errors.add("Пользователь с таким email уже существует");
        }
        if (userDto.getUsername().isBlank()) {
            errors.add("Значение имени не может быть пустым");
        }
        if (userService.findByUsername(userDto.getUsername()).isPresent()) {
            errors.add("Пользователь с таким именем уже существует");
        }
        if (userDto.getPassword().isBlank()) {
            errors.add("Значение пароля не может быть пустым");
        }
        userDto.setRoles(new ArrayList<>());
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
