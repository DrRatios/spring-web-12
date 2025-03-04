package com.aleksgolds.spring.web.auth;

import com.aleksgolds.spring.web.auth.entities.Role;
import com.aleksgolds.spring.web.auth.entities.User;
import com.aleksgolds.spring.web.auth.repositories.UserRepository;
import com.aleksgolds.spring.web.auth.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@SpringBootTest
public class AuthTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void findUserTest() {
        Role role = new Role();
        role.setName("admin");
        Collection<Role> roles = new ArrayList<>();
        roles.add(role);
        User user = new User();
        user.setId(1L);
        user.setUsername("admin");
        user.setPassword("admin");
        user.setEmail("admin@admin.com");
        user.setRoles(roles);

        Mockito.doReturn(Optional.of(user)).when(userRepository).findByUsername("admin");
        Mockito.doReturn(Optional.of(user)).when(userRepository).findByEmail("admin@admin.com");
        Mockito.doReturn(Optional.of(user)).when(userRepository).findById(1L);

        Assertions.assertNotNull(user);

        Assertions.assertEquals(user, userService.findUserById(1L));
        Assertions.assertEquals(roles, userService.findUserById(1L).getRoles());
        Assertions.assertEquals(Optional.of(user), userService.findByEmail("admin@admin.com"));
        Assertions.assertEquals(Optional.of(user), userService.findByUsername("admin"));
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(ArgumentMatchers.eq("admin"));
    }

}
