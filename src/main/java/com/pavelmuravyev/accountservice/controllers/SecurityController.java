package com.pavelmuravyev.accountservice.controllers;

import com.pavelmuravyev.accountservice.dto.SecurityEventDTO;
import com.pavelmuravyev.accountservice.dto.mappers.SecurityEventMapper;
import com.pavelmuravyev.accountservice.models.SecurityEvent;
import com.pavelmuravyev.accountservice.services.SecurityEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class SecurityController {

    private final SecurityEventService securityEventService;
    private final SecurityEventMapper securityEventMapper;

    @Autowired
    public SecurityController(SecurityEventService securityEventService,
                              SecurityEventMapper securityEventMapper) {
        this.securityEventService = securityEventService;
        this.securityEventMapper = securityEventMapper;
    }

    @GetMapping("/api/security/events")
    public List<SecurityEventDTO> getAllSecurityEvents() {
        List<SecurityEvent> eventList = securityEventService.getAllEvents();
        if (!eventList.isEmpty()) {
            List<SecurityEventDTO> dtoList = new ArrayList<>(eventList.size());
            eventList.forEach(event -> dtoList.add(securityEventMapper.toSecurityEventDto(event)));
            return dtoList;
        } else {
            return Collections.emptyList();
        }
    }
}
