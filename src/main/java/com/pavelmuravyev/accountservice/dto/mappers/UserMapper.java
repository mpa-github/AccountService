package com.pavelmuravyev.accountservice.dto.mappers;

import com.pavelmuravyev.accountservice.dto.ChangePassStatusDTO;
import com.pavelmuravyev.accountservice.dto.UserDTO;
import com.pavelmuravyev.accountservice.dto.UserRegistrationDTO;
import com.pavelmuravyev.accountservice.models.Group;
import com.pavelmuravyev.accountservice.models.User;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public User toUser(UserRegistrationDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setLastname(dto.getLastname());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }

    public UserDTO toUserDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setLastname(user.getLastname());
        dto.setEmail(user.getEmail().toLowerCase());
        dto.setRoles(user.getUserGroups()
                         .stream()
                         .map(Group::getName)
                         .sorted()
                         .collect(Collectors.toCollection(LinkedHashSet::new)));
        return dto;
    }

    public ChangePassStatusDTO toChangePassStatusDTO(User user) {
        ChangePassStatusDTO dto = new ChangePassStatusDTO();
        dto.setEmail(user.getEmail().toLowerCase());
        dto.setStatus("The password has been updated successfully");
        return dto;
    }
}
