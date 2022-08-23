package com.pavelmuravyev.accountservice.controllers;

import com.pavelmuravyev.accountservice.dto.*;
import com.pavelmuravyev.accountservice.dto.mappers.UserMapper;
import com.pavelmuravyev.accountservice.models.User;
import com.pavelmuravyev.accountservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("api/admin/user")
    public List<UserDTO> getUsersInfo() {
        List<User> userList = userService.getAllUsers();
        if (!userList.isEmpty()) {
            List<UserDTO> dtoList = new ArrayList<>(userList.size());
            userList.forEach(user -> dtoList.add(userMapper.toUserDto(user)));
            return dtoList;
        } else {
            return Collections.emptyList();
        }
    }

    @DeleteMapping(value = {"api/admin/user/{email}", "api/admin/user/", "api/admin/user"})
    public DeleteUserStatusDTO deleteUser(@PathVariable String email,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        return userService.deleteUser(email, userDetails);
    }

    @PutMapping("api/admin/user/role")
    public UserDTO updateUserRole(@Valid @RequestBody UpdateGroupDTO dto,
                                  @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.updateUserRole(dto, userDetails);
        return userMapper.toUserDto(user);
    }

    @PutMapping("api/admin/user/access")
    public StatusDTO updateUserAccess(@Valid @RequestBody UpdateAccessDTO dto,
                                      @AuthenticationPrincipal UserDetails userDetails) {
        return userService.updateUserAccess(dto, userDetails);
    }
}
