package com.aleksgolds.spring.web.auth.conventers;


import com.aleksgolds.spring.web.auth.dto.UserDto;
import com.aleksgolds.spring.web.auth.entities.User;
import org.springframework.stereotype.Component;

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
