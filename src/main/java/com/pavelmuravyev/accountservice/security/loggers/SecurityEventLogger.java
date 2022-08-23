package com.pavelmuravyev.accountservice.security.loggers;

import com.pavelmuravyev.accountservice.security.enums.SecurityAction;
import com.pavelmuravyev.accountservice.models.SecurityEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Component
public class SecurityEventLogger {

    private final HttpServletRequest request;

    @Autowired
    public SecurityEventLogger(HttpServletRequest request) {
        this.request = request;
    }

    public SecurityEvent log(SecurityAction action, String subject, String object) {
        SecurityEvent event = new SecurityEvent();
        event.setDate(LocalDateTime.now());
        event.setAction(action);
        event.setSubject(subject);
        event.setObject(object);
        event.setPath(request.getRequestURI());
        return event;
    }

    public SecurityEvent log(SecurityAction action, String subject) {
        SecurityEvent event = new SecurityEvent();
        event.setDate(LocalDateTime.now());
        event.setAction(action);
        event.setSubject(subject);
        event.setObject(request.getRequestURI());
        event.setPath(request.getRequestURI());
        return event;
    }
}
