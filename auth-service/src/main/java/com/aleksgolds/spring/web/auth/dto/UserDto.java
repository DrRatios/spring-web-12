package com.aleksgolds.spring.web.auth.dto;


import com.aleksgolds.spring.web.auth.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Collection<Role> roles;

}
