package com.pavelmuravyev.accountservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ChangePassDTO {

    @JsonProperty(value = "new_password")
    @NotBlank(message = "Field 'new_password' shouldn't be empty! - @Valid @NotBlank")
    @Size(min = 12, message = "Password length must be 12 chars minimum! - @Valid @Size")
    private String newPassword;

    public ChangePassDTO() {
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
