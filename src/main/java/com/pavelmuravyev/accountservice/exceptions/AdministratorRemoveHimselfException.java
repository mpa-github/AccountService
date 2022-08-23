package com.pavelmuravyev.accountservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Can't remove ADMINISTRATOR role!")
public class AdministratorRemoveHimselfException extends RuntimeException {
}
