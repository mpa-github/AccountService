package com.pavelmuravyev.accountservice.dbutils;

import com.pavelmuravyev.accountservice.security.enums.SecurityAction;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class SecurityActionEnumStringConverter implements AttributeConverter<SecurityAction, String> {

    @Override
    public String convertToDatabaseColumn(SecurityAction attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public SecurityAction convertToEntityAttribute(String dbData) {
        return dbData == null ? null : SecurityAction.valueOf(dbData);
    }
}
