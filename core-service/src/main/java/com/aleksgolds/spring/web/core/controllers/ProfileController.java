package com.aleksgolds.spring.web.core.controllers;

import com.aleksgolds.spring.web.core.dto.ProfileDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    @GetMapping
    public ProfileDto getCurrentUserInfo(@RequestHeader String username) {
        // User user = userService.findByUsername(principal.getName());
        return new ProfileDto(username);
    }
}
