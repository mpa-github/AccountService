package com.pavelmuravyev.accountservice.dto;

public class DeleteUserStatusDTO {

    private String user;
    private String status;

    public DeleteUserStatusDTO() {
    }

    public DeleteUserStatusDTO(String user, String status) {
        this.user = user;
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
