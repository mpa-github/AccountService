package com.pavelmuravyev.accountservice.eventhandlers;

import com.pavelmuravyev.accountservice.security.enums.SecurityAction;
import com.pavelmuravyev.accountservice.security.loggers.SecurityEventLogger;
import com.pavelmuravyev.accountservice.models.SecurityEvent;
import com.pavelmuravyev.accountservice.services.LoginAttemptService;
import com.pavelmuravyev.accountservice.services.SecurityEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private final HttpServletRequest request;
    private final LoginAttemptService loginAttemptService;
    private final SecurityEventService securityEventService;
    private final SecurityEventLogger eventLogger;

    @Autowired
    public AuthenticationFailureListener(HttpServletRequest request,
                                         LoginAttemptService loginAttemptService,
                                         SecurityEventService securityEventService,
                                         SecurityEventLogger eventLogger) {
        this.request = request;
        this.loginAttemptService = loginAttemptService;
        this.securityEventService = securityEventService;
        this.eventLogger = eventLogger;
    }

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        String userEmail = event.getAuthentication().getName();
        SecurityEvent securityEvent = eventLogger.log(SecurityAction.LOGIN_FAILED, userEmail);
        securityEventService.registerEvent(securityEvent);
        loginAttemptService.loginFailed(userEmail);
    }
}
