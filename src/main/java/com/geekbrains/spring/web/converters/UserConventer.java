package com.geekbrains.spring.web.converters;

import com.geekbrains.spring.web.dto.ProductDto;
import com.geekbrains.spring.web.dto.UserDto;
import com.geekbrains.spring.web.entities.Product;
import com.geekbrains.spring.web.entities.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserConventer {

    public User dtoToEntity(UserDto userDto) {
        return new User(userDto.getId(), userDto.getUsername(), userDto.getPassword(),
                userDto.getEmail(), userDto.getRoles());
    }

    public UserDto entityToDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getPassword(),
                user.getEmail(), user.getRoles());
    }

}
