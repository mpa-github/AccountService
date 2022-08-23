package com.pavelmuravyev.accountservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UpdateAccessDTO {

    @NotBlank(message = "Field 'user' shouldn't be empty! - @Valid @NotBlank")
    @JsonProperty(value = "user")
    private String userEmail;

    @NotBlank(message = "Field 'operation' shouldn't be empty! - @Valid @NotBlank")
    @Pattern(regexp = "LOCK|UNLOCK", flags = Pattern.Flag.CASE_INSENSITIVE,
             message = "Wrong operation name! - @Valid @Pattern")
    private String operation;

    public UpdateAccessDTO() {
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
