package com.pavelmuravyev.accountservice.dto.mappers;

import com.pavelmuravyev.accountservice.dto.SecurityEventDTO;
import com.pavelmuravyev.accountservice.models.SecurityEvent;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class SecurityEventMapper {

    public SecurityEventDTO toSecurityEventDto(SecurityEvent securityEvent) {
        SecurityEventDTO dto = new SecurityEventDTO();
        dto.setId(securityEvent.getId());
        dto.setDate(securityEvent.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS")));
        dto.setAction(securityEvent.getAction().name());
        dto.setSubject(securityEvent.getSubject());
        dto.setObject(securityEvent.getObject());
        dto.setPath(securityEvent.getPath());
        return dto;
    }
}
