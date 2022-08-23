package com.pavelmuravyev.accountservice.dbutils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;

@Converter
public class YearMonthSqlDateConverter implements AttributeConverter<YearMonth, Date> {

    @Override
    public Date convertToDatabaseColumn(YearMonth attribute) {
        return attribute == null ? null : Date.valueOf(attribute.atDay(1));
    }

    @Override
    public YearMonth convertToEntityAttribute(Date dbData) {
        LocalDate localDate = dbData.toLocalDate();
        return localDate == null ? null : YearMonth.of(localDate.getYear(), localDate.getMonthValue());
    }
}
