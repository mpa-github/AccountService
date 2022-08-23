package com.pavelmuravyev.accountservice.services;

import com.pavelmuravyev.accountservice.security.enums.SecurityAction;
import com.pavelmuravyev.accountservice.security.loggers.SecurityEventLogger;
import com.pavelmuravyev.accountservice.models.SecurityEvent;
import com.pavelmuravyev.accountservice.models.User;
import com.pavelmuravyev.accountservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginAttemptService {

    private static final int MAX_FAILED_ATTEMPTS = 5;
    private final UserRepository userRepository;
    private final SecurityEventService securityEventService;
    private final SecurityEventLogger eventLogger;

    @Autowired
    public LoginAttemptService(UserRepository userRepository,
                               SecurityEventService securityEventService,
                               SecurityEventLogger eventLogger) {
        this.userRepository = userRepository;
        this.securityEventService = securityEventService;
        this.eventLogger = eventLogger;
    }

    public void loginFailed(String email) {
        Optional<User> optUser = userRepository.findUserByEmailIgnoreCase(email);
        if (optUser.isPresent()) {
            User user = optUser.get();
            int attemptCount = user.getFailedAttempt() + 1;
            if (attemptCount == MAX_FAILED_ATTEMPTS) {
                user.setFailedAttempt(attemptCount);
                SecurityEvent event = eventLogger.log(SecurityAction.BRUTE_FORCE, user.getEmail());
                securityEventService.registerEvent(event);
                if (!user.getType().equals("Administrative")) {
                    lockUser(user);
                    event = eventLogger.log(
                            SecurityAction.LOCK_USER, user.getEmail(), "Lock user " + user.getEmail());
                    securityEventService.registerEvent(event);
                }
            } else {
                user.setFailedAttempt(attemptCount);
                userRepository.save(user);
            }
        }
    }

    public void loginSucceeded(String email) {
        Optional<User> optUser = userRepository.findUserByEmailIgnoreCase(email);
        if (optUser.isPresent()) {
            User user = optUser.get();
            user.setFailedAttempt(0);
            userRepository.save(user);
        }
    }

    private void lockUser(User user) {
        user.setAccountNonLocked(false);
        userRepository.save(user);
    }
}
