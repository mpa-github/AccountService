package com.pavelmuravyev.accountservice.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRegistrationDTO {

    @NotBlank(message = "Field 'name' shouldn't be empty! - @Valid @NotBlank")
    private String name;

    @NotBlank(message = "Field 'lastname' shouldn't be empty! - @Valid @NotBlank")
    private String lastname;

    @NotBlank(message = "Field 'email' shouldn't be empty! - @Valid @NotBlank")
    @Pattern(regexp = ".+@acme.com", message = "Email format is invalid! - @Valid @Pattern")
    private String email;

    @NotBlank(message = "Field 'password' shouldn't be empty! - @Valid @NotBlank")
    @Size(min = 12, message = "Password length must be 12 chars minimum! - @Valid @Size")
    private String password;

    public UserRegistrationDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
