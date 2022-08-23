package com.pavelmuravyev.accountservice.services;

import com.pavelmuravyev.accountservice.models.SecurityEvent;
import com.pavelmuravyev.accountservice.repositories.SecurityEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityEventService {

    private final SecurityEventRepository securityEventRepository;

    @Autowired
    public SecurityEventService(SecurityEventRepository securityEventRepository) {
        this.securityEventRepository = securityEventRepository;
    }

    public void registerEvent(SecurityEvent event) {
        securityEventRepository.save(event);
    }

    public List<SecurityEvent> getAllEvents() {
        return securityEventRepository.findAllByOrderByIdAsc();
    }
}
