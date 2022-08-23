package com.pavelmuravyev.accountservice.services;

import com.pavelmuravyev.accountservice.security.enums.SecurityAction;
import com.pavelmuravyev.accountservice.security.loggers.SecurityEventLogger;
import com.pavelmuravyev.accountservice.models.SecurityEvent;
import com.pavelmuravyev.accountservice.repositories.GroupRepository;
import com.pavelmuravyev.accountservice.repositories.UserRepository;
import com.pavelmuravyev.accountservice.exceptions.*;
import com.pavelmuravyev.accountservice.models.User;
import com.pavelmuravyev.accountservice.security.passwordstorage.HackedPassStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final HackedPassStorage hackedPassStorage;
    private final SecurityEventService securityEventService;
    private final SecurityEventLogger eventLogger;

    @Autowired
    public AuthService(UserRepository userRepository,
                       GroupRepository groupRepository,
                       PasswordEncoder passwordEncoder,
                       HackedPassStorage hackedPassStorage,
                       SecurityEventService securityEventService,
                       SecurityEventLogger eventLogger) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.bCryptPasswordEncoder = passwordEncoder;
        this.hackedPassStorage = hackedPassStorage;
        this.securityEventService = securityEventService;
        this.eventLogger = eventLogger;
    }

    public User registerUser(User user) {
        Optional<User> optUser = userRepository.findUserByEmailIgnoreCase(user.getEmail());
        if (optUser.isPresent()){
            throw new UserAlreadyExistException();
        } else {
            String password = user.getPassword();
            runPasswordValidation(password);
            user.setEmail(user.getEmail().toLowerCase());
            user.setPassword(bCryptPasswordEncoder.encode(password));
            user.setFailedAttempt(0);
            user.setAccountNonLocked(true);
            if (userRepository.count() == 0) {
                user.addGroup(groupRepository.findGroupByName("ROLE_ADMINISTRATOR"));
                user.setType("Administrative");
            } else {
                user.addGroup(groupRepository.findGroupByName("ROLE_USER"));
                user.setType("Business");
            }
            User updatedUser = userRepository.save(user);
            SecurityEvent event = eventLogger.log(SecurityAction.CREATE_USER, "Anonymous", user.getEmail());
            securityEventService.registerEvent(event);
            return updatedUser;
        }
    }

    public User changePassword(UserDetails userDetails, String newPassword) {
        User authUser = userRepository.findUserByEmailIgnoreCase(userDetails.getUsername())
                                      .orElseThrow(() -> new UserNotExistException("User not found!"));
        runPasswordValidation(newPassword);
        if (bCryptPasswordEncoder.matches(newPassword, authUser.getPassword())) {
            throw new SamePasswordsException();
        }
        authUser.setPassword(bCryptPasswordEncoder.encode(newPassword));
        User updatedUser = userRepository.save(authUser);
        SecurityEvent event = eventLogger.log(SecurityAction.CHANGE_PASSWORD, authUser.getEmail(), authUser.getEmail());
        securityEventService.registerEvent(event);
        return updatedUser;
    }

    public void runPasswordValidation(String password) {
        if (hackedPassStorage.isPasswordHacked(password)) {
            throw new HackedPasswordException();
        }
    }
}
