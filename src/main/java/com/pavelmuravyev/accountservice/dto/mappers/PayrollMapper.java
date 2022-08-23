package com.pavelmuravyev.accountservice.dto.mappers;

import com.pavelmuravyev.accountservice.dto.AddPayrollDTO;
import com.pavelmuravyev.accountservice.dto.PayrollDTO;
import com.pavelmuravyev.accountservice.models.Payroll;
import org.springframework.stereotype.Component;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class PayrollMapper {

    public Payroll toPayroll(AddPayrollDTO dto) {
        Payroll payroll = new Payroll();
        YearMonth period = YearMonth.parse(dto.getPeriod(), DateTimeFormatter.ofPattern("MM-yyyy"));
        payroll.setPeriod(period);
        payroll.setSalary(dto.getSalary());
        payroll.setEmployeeEmail(dto.getUserEmail());
        payroll.setUser(null);
        return payroll;
    }

    public PayrollDTO toPayrollDto(Payroll payroll) {
        PayrollDTO dto = new PayrollDTO();
        dto.setName(payroll.getUser().getName());
        dto.setLastname(payroll.getUser().getLastname());
        dto.setPeriod(payroll.getPeriod().format(DateTimeFormatter.ofPattern("MMMM-yyyy", Locale.ENGLISH)));
        dto.setSalary(getCentsAsSalaryString(payroll.getSalary()));
        return dto;
    }

    private String getCentsAsSalaryString(Long cents) {
        Long dollars = cents / 100;
        Long centReminder = cents % 100;
        return String.format("%d dollar(s) %d cent(s)", dollars, centReminder);
    }
}
