package com.pavelmuravyev.accountservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UpdateGroupDTO {

    @NotBlank(message = "Field 'user' shouldn't be empty! - @Valid @NotBlank")
    @JsonProperty(value = "user")
    private String userEmail;

    @NotBlank(message = "Field 'role' shouldn't be empty! - @Valid @NotBlank")
    private String role;

    @NotBlank(message = "Field 'operation' shouldn't be empty! - @Valid @NotBlank")
    @Pattern(regexp = "GRANT|REMOVE", flags = Pattern.Flag.CASE_INSENSITIVE,
             message = "Wrong operation name! - @Valid @Pattern")
    private String operation;

    public UpdateGroupDTO() {
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
