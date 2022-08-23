package com.pavelmuravyev.accountservice.controllers;

import com.pavelmuravyev.accountservice.dto.AddPayrollDTO;
import com.pavelmuravyev.accountservice.dto.PayrollDTO;
import com.pavelmuravyev.accountservice.dto.StatusDTO;
import com.pavelmuravyev.accountservice.dto.mappers.PayrollMapper;
import com.pavelmuravyev.accountservice.models.Payroll;

import com.pavelmuravyev.accountservice.services.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@Validated
public class PaymentController {

    private final PayrollService payrollService;
    private final PayrollMapper payrollMapper;

    @Autowired
    public PaymentController(PayrollService payrollService, PayrollMapper payrollMapper) {
        this.payrollService = payrollService;
        this.payrollMapper = payrollMapper;
    }

    @PostMapping("api/acct/payments")
    public StatusDTO postPayrolls(@RequestBody List<@Valid AddPayrollDTO> dtoList) {
        List<Payroll> payrollList = new ArrayList<>(dtoList.size());
        dtoList.forEach(dto -> payrollList.add(payrollMapper.toPayroll(dto)));
        return payrollService.savePayrolls(payrollList);
    }

    @PutMapping("api/acct/payments")
    public StatusDTO updatePayroll(@Valid @RequestBody AddPayrollDTO dto) {
        return payrollService.updatePayroll(payrollMapper.toPayroll(dto));
    }

    @GetMapping("/api/empl/payment")
    public ResponseEntity<Object> getPayrolls(@AuthenticationPrincipal UserDetails userDetails,
                                              @RequestParam(name = "period", required = false)
                                              @Pattern(regexp = "(0[1-9]|1[0-2])-2\\d{3}",
                                                       message = "Wrong date format! - @Validated")
                                              String period) {
        if (period == null) {
            List<Payroll> payrollList = payrollService.getAllPayrollsByEmployee(userDetails.getUsername());
            if (!payrollList.isEmpty()) {
                List<PayrollDTO> dtoList = new ArrayList<>(payrollList.size());
                payrollList.forEach(payroll -> dtoList.add(payrollMapper.toPayrollDto(payroll)));
                return new ResponseEntity<>(dtoList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } else {
            YearMonth periodYM = YearMonth.parse(period, DateTimeFormatter.ofPattern("MM-yyyy"));
            Optional<Payroll> optPayroll = payrollService.getEmployeePayrollByPeriod(userDetails.getUsername(),
                                                                                     periodYM);
            if (optPayroll.isPresent()) {
                PayrollDTO dto = payrollMapper.toPayrollDto(optPayroll.get());
                return new ResponseEntity<>(dto, HttpStatus.OK);

            } else {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
    }
}