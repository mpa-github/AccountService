package com.pavelmuravyev.accountservice.models;

import com.pavelmuravyev.accountservice.dbutils.YearMonthSqlDateConverter;

import javax.persistence.*;
import java.time.YearMonth;
import java.util.Objects;

@Entity
@Table(name = "PAYROLLS")
public class Payroll {

    @Column(name = "PAYROLL_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "PAYROLL_EMPLOYEE")
    //@Transient
    private String employeeEmail;

    @Column(name = "PAYROLL_PERIOD")
    @Convert(converter = YearMonthSqlDateConverter.class)
    private YearMonth period;

    @Column(name = "PAYROLL_SALARY")
    private Long salary;

    @ManyToOne
    @JoinColumn(name = "FK_USER_ID", nullable = false)
    private User user;

    public Payroll() {
    }

    public Payroll(Long id, String employeeEmail, YearMonth period, Long salary, User user) {
        this.id = id;
        this.employeeEmail = employeeEmail;
        this.period = period;
        this.salary = salary;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public YearMonth getPeriod() {
        return period;
    }

    public void setPeriod(YearMonth period) {
        this.period = period;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Payroll{" +
                "id=" + id +
                ", employeeEmail='" + employeeEmail + '\'' +
                ", period=" + period +
                ", salary=" + salary +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payroll payroll = (Payroll) o;
        return Objects.equals(id, payroll.id) &&
               Objects.equals(employeeEmail, payroll.employeeEmail) &&
               Objects.equals(period, payroll.period) &&
               Objects.equals(salary, payroll.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employeeEmail, period, salary);
    }
}
