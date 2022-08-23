package com.pavelmuravyev.accountservice.services;

import com.pavelmuravyev.accountservice.repositories.PayrollRepository;
import com.pavelmuravyev.accountservice.repositories.UserRepository;
import com.pavelmuravyev.accountservice.dto.PayrollDTO;
import com.pavelmuravyev.accountservice.dto.StatusDTO;
import com.pavelmuravyev.accountservice.exceptions.DuplicatePaymentPeriodException;
import com.pavelmuravyev.accountservice.exceptions.NegativeSalaryException;
import com.pavelmuravyev.accountservice.exceptions.UserNotExistException;
import com.pavelmuravyev.accountservice.exceptions.WrongPaymentPeriodException;
import com.pavelmuravyev.accountservice.models.Payroll;
import com.pavelmuravyev.accountservice.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class PayrollService {

    private final PayrollRepository payrollRepository;
    private final UserRepository userRepository;

    @Autowired
    public PayrollService(PayrollRepository payrollRepository, UserRepository userRepository) {
        this.payrollRepository = payrollRepository;
        this.userRepository = userRepository;
    }

    public Optional<Payroll> getEmployeePayrollByPeriod(String employeeEmail, YearMonth period) {
        return payrollRepository.findPayrollByEmployeeEmailIgnoreCaseAndPeriod(employeeEmail, period);
    }

    public List<Payroll> getAllPayrollsByEmployee(String employeeEmail) {
        return payrollRepository.findPayrollsByEmployeeEmailIgnoreCaseOrderByPeriodDesc(employeeEmail);
    }

    @Transactional
    public StatusDTO savePayrolls(List<Payroll> payrolls) {
        for (Payroll payroll : payrolls) {
            String email =  payroll.getEmployeeEmail();
            Optional<User> optUser = userRepository.findUserByEmailIgnoreCase(email);
            if (optUser.isEmpty()) {
                throw new UserNotExistException(email + ": Employee not exist!");
            }
            if (payrollRepository.existsPayrollByEmployeeEmailIgnoreCaseAndPeriod(email, payroll.getPeriod())) {
                throw new DuplicatePaymentPeriodException(email + ": Duplicate salary for the period!");
            }
            payroll.setUser(optUser.get());
            payrollRepository.save(payroll);
        }
        return new StatusDTO("Added successfully!");
    }

    public StatusDTO updatePayroll(Payroll payroll) {
        String email =  payroll.getEmployeeEmail();
        YearMonth period = payroll.getPeriod();
        Optional<Payroll> optPayroll = payrollRepository.findPayrollByEmployeeEmailIgnoreCaseAndPeriod(email, period);
        if (optPayroll.isEmpty()) {
            throw new UserNotExistException("Employee not exist!");
        }
        Payroll payrollToUpdate = optPayroll.get();
        payrollToUpdate.setSalary(payroll.getSalary());
        payrollRepository.save(payrollToUpdate);
        return new StatusDTO("Updated successfully!");
    }
}
