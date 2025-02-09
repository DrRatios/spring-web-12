package com.geekbrains.spring.web.validators;

import com.geekbrains.spring.web.dto.ProductDto;
import com.geekbrains.spring.web.dto.UserDto;
import com.geekbrains.spring.web.exceptions.ValidationException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserValidator {

    public void validate(UserDto userDto) throws ValidationException {
        List<String> errors = new ArrayList<>();
        if (userDto.getEmail().isBlank()) {
            errors.add("Значение email не может быть пустым");
        }
        if (userDto.getUsername().isBlank()) {
            errors.add("Значение имени не может быть пустым");
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
