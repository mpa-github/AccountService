package com.pavelmuravyev.accountservice.dto;

import java.util.LinkedHashSet;

public class UserDTO {

    private Long id;
    private String name;
    private String lastname;
    private String email;
    private LinkedHashSet<String> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LinkedHashSet<String> getRoles() {
        return roles;
    }

    public void setRoles(LinkedHashSet<String> roles) {
        this.roles = roles;
    }
}
