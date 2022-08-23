package com.pavelmuravyev.accountservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class AddPayrollDTO {

    @NotBlank(message = "Field 'employee' shouldn't be empty! - @Valid @NotBlank")
    @JsonProperty(value = "employee")
    private String userEmail;

    @NotBlank(message = "Field 'period' shouldn't be empty! - @Valid @NotBlank")
    @Pattern(regexp = "(0[1-9]|1[0-2])-2\\d{3}", message = "Wrong date format! - @Valid @Pattern")
    private String period;

    @Min(value = 1, message = "Salary must be non negative or zero! - @Valid @Min")
    private Long salary;

    public AddPayrollDTO() {
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }
}
