package com.pavelmuravyev.accountservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class NegativeSalaryException extends RuntimeException {

    public NegativeSalaryException(String message) {
        super(message);
    }
}
