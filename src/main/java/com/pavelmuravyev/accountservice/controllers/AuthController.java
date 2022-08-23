package com.pavelmuravyev.accountservice.controllers;

import com.pavelmuravyev.accountservice.dto.ChangePassDTO;
import com.pavelmuravyev.accountservice.dto.ChangePassStatusDTO;
import com.pavelmuravyev.accountservice.dto.UserDTO;
import com.pavelmuravyev.accountservice.dto.UserRegistrationDTO;
import com.pavelmuravyev.accountservice.dto.mappers.UserMapper;
import com.pavelmuravyev.accountservice.models.User;
import com.pavelmuravyev.accountservice.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

    private final AuthService authService;
    private final UserMapper userMapper;

    @Autowired
    public AuthController(AuthService authService, UserMapper userMapper) {
        this.authService = authService;
        this.userMapper = userMapper;
    }

    @PostMapping("/api/auth/signup")
    public UserDTO addUser(@Valid @RequestBody UserRegistrationDTO dto) {
        User newUser = authService.registerUser(userMapper.toUser(dto));
        return userMapper.toUserDto(newUser);
    }

    @PostMapping("/api/auth/changepass")
    public ChangePassStatusDTO changeUserPassword(@Valid @RequestBody ChangePassDTO dto,
                                                  @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = authService.changePassword(userDetails, dto.getNewPassword());
        return userMapper.toChangePassStatusDTO(currentUser);
    }
}
