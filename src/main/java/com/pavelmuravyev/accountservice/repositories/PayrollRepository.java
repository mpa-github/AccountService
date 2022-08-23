package com.pavelmuravyev.accountservice.repositories;

import com.pavelmuravyev.accountservice.models.Payroll;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Repository
public interface PayrollRepository extends CrudRepository<Payroll, Long> {

    public List<Payroll> findPayrollsByEmployeeEmailIgnoreCaseOrderByPeriodDesc(String employeeEmail);
    public Optional<Payroll> findPayrollByEmployeeEmailIgnoreCaseAndPeriod(String employeeEmail, YearMonth period);
    public boolean existsPayrollByEmployeeEmailIgnoreCaseAndPeriod(String employeeEmail, YearMonth period);
}
