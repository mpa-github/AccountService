package com.pavelmuravyev.accountservice.security.passwordstorage;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HackedPassStorage {

    private static final List<String> BREACHED_PASSWORDS = List.of(
            "PasswordForJanuary", "PasswordForFebruary", "PasswordForMarch",
            "PasswordForApril", "PasswordForMay", "PasswordForJune",
            "PasswordForJuly", "PasswordForAugust", "PasswordForSeptember",
            "PasswordForOctober", "PasswordForNovember", "PasswordForDecember");

    public boolean isPasswordHacked(String password) {
        return BREACHED_PASSWORDS.contains(password);
    }
}
