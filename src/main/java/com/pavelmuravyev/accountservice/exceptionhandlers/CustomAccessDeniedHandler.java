package com.pavelmuravyev.accountservice.exceptionhandlers;

import com.pavelmuravyev.accountservice.security.enums.SecurityAction;
import com.pavelmuravyev.accountservice.security.loggers.SecurityEventLogger;
import com.pavelmuravyev.accountservice.models.SecurityEvent;
import com.pavelmuravyev.accountservice.services.SecurityEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final SecurityEventService securityEventService;
    private final SecurityEventLogger eventLogger;

    @Autowired
    public CustomAccessDeniedHandler(SecurityEventService securityEventService,
                                     SecurityEventLogger eventLogger) {
        this.securityEventService = securityEventService;
        this.eventLogger = eventLogger;
    }

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.sendError(HttpStatus.FORBIDDEN.value(), "Access Denied!");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SecurityEvent event = eventLogger.log(SecurityAction.ACCESS_DENIED, auth.getName().toLowerCase());
        securityEventService.registerEvent(event);
    }
}
