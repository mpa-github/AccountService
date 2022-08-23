package com.pavelmuravyev.accountservice.services;

import com.pavelmuravyev.accountservice.dto.DeleteUserStatusDTO;
import com.pavelmuravyev.accountservice.dto.StatusDTO;
import com.pavelmuravyev.accountservice.dto.UpdateAccessDTO;
import com.pavelmuravyev.accountservice.dto.UpdateGroupDTO;
import com.pavelmuravyev.accountservice.security.enums.SecurityAction;
import com.pavelmuravyev.accountservice.exceptions.AdministratorRemoveHimselfException;
import com.pavelmuravyev.accountservice.exceptions.BadRequestException;
import com.pavelmuravyev.accountservice.exceptions.NotFoundException;
import com.pavelmuravyev.accountservice.security.loggers.SecurityEventLogger;
import com.pavelmuravyev.accountservice.models.Group;
import com.pavelmuravyev.accountservice.models.SecurityEvent;
import com.pavelmuravyev.accountservice.models.User;
import com.pavelmuravyev.accountservice.repositories.GroupRepository;
import com.pavelmuravyev.accountservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final SecurityEventService securityEventService;
    private final SecurityEventLogger eventLogger;

    @Autowired
    public UserService(UserRepository userRepository,
                       GroupRepository groupRepository,
                       SecurityEventService securityEventService,
                       SecurityEventLogger eventLogger) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.securityEventService = securityEventService;
        this.eventLogger = eventLogger;
    }

    public List<User> getAllUsers() {
        return userRepository.findAllByOrderByIdAsc();
    }

    @Transactional
    public DeleteUserStatusDTO deleteUser(String email, UserDetails userDetails) {
        if (email.equalsIgnoreCase(userDetails.getUsername())) {
            throw new AdministratorRemoveHimselfException();
        }
        if (userRepository.existsUserByEmailIgnoreCase(email)) {
            userRepository.deleteUserByEmailIgnoreCase(email);
            SecurityEvent event = eventLogger.log(
                    SecurityAction.DELETE_USER, userDetails.getUsername(), email);
            securityEventService.registerEvent(event);
            return new DeleteUserStatusDTO(email.toLowerCase(), "Deleted successfully!");
        } else {
            throw new NotFoundException("User not found!");
        }
    }

    public User updateUserRole(UpdateGroupDTO dto, UserDetails userDetails) {
        String email = dto.getUserEmail();
        String role = "ROLE_" + dto.getRole().toUpperCase();
        String operation = dto.getOperation().toUpperCase();
        Optional<User> optUser = userRepository.findUserByEmailIgnoreCase(email);
        Optional<Group> optGroup = groupRepository.findGroupByNameIgnoreCase(role);
        if (optUser.isEmpty()) {
            throw new NotFoundException("User not found!");
        }
        if (optGroup.isEmpty()) {
            throw new NotFoundException("Role not found!");
        }
        User user = optUser.get();
        Group group = optGroup.get();
        SecurityEvent event;
        User updatedUser;
        switch (operation) {
            case ("GRANT"):
                if (user.getType().equals("Administrative") &&
                        (role.equals("ROLE_USER") || role.equals("ROLE_ACCOUNTANT") || role.equals("ROLE_AUDITOR"))) {
                    throw new BadRequestException("The user cannot combine administrative and business roles!");
                }
                if (user.getType().equals("Business") && role.equals("ROLE_ADMINISTRATOR")) {
                    throw new BadRequestException("The user cannot combine administrative and business roles!");
                }
                user.addGroup(optGroup.get());
                updatedUser = userRepository.save(user);
                event = eventLogger.log(
                        SecurityAction.GRANT_ROLE,
                        userDetails.getUsername().toLowerCase(),
                        "Grant role " + dto.getRole().toUpperCase() + " to " + updatedUser.getEmail());
                securityEventService.registerEvent(event);
                return updatedUser;
            case ("REMOVE"):
                if(group.getName().equals("ROLE_ADMINISTRATOR")) {
                    throw new BadRequestException("Can't remove ADMINISTRATOR role!");
                }
                if (!user.getUserGroups().contains(group)) {
                    throw new BadRequestException("The user does not have a role!");
                }
                if (user.getUserGroups().size() == 1) {
                    throw new BadRequestException("The user must have at least one role!");
                }
                user.removeGroup(group);
                updatedUser = userRepository.save(user);
                event = eventLogger.log(
                        SecurityAction.REMOVE_ROLE,
                        userDetails.getUsername().toLowerCase(),
                        "Remove role " + dto.getRole().toUpperCase() + " from " + updatedUser.getEmail());
                securityEventService.registerEvent(event);
                return updatedUser;
            default:
                throw new BadRequestException("Wrong operation name!");
        }
    }

    public StatusDTO updateUserAccess(UpdateAccessDTO dto, UserDetails userDetails) {
        String email = dto.getUserEmail();
        String operation = dto.getOperation().toUpperCase();
        Optional<User> optUser = userRepository.findUserByEmailIgnoreCase(email);
        if (optUser.isEmpty()) {
            throw new NotFoundException("User not found!");
        }
        User user = optUser.get();
        SecurityEvent event;
        switch (operation) {
            case ("LOCK"):
                if(user.getType().equals("Administrative")) {
                    throw new BadRequestException("Can't lock the ADMINISTRATOR!");
                }
                user.setAccountNonLocked(false);
                userRepository.save(user);
                event = eventLogger.log(
                        SecurityAction.LOCK_USER,
                        userDetails.getUsername().toLowerCase(),
                        "Lock user " + user.getEmail());
                securityEventService.registerEvent(event);
                break;
            case ("UNLOCK"):
                user.setAccountNonLocked(true);
                user.setFailedAttempt(0);
                userRepository.save(user);
                event = eventLogger.log(
                        SecurityAction.UNLOCK_USER,
                        userDetails.getUsername().toLowerCase(),
                        "Unlock user " + user.getEmail());
                securityEventService.registerEvent(event);
                break;
            default:
                throw new BadRequestException("Wrong operation name!");
        }
        return new StatusDTO(String.format("User %s %sed!", email.toLowerCase(), operation.toLowerCase()));
    }
}
