package com.pavelmuravyev.accountservice.exceptionhandlers;

import com.pavelmuravyev.accountservice.exceptions.CustomErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.StringJoiner;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // error handle for @Validated
    @ExceptionHandler(ConstraintViolationException.class)
    public void handleValidated(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    // error handle for @Valid
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        StringJoiner stringJoiner = new StringJoiner("; ");
        ex.getBindingResult()
          .getFieldErrors()
          .forEach(error -> stringJoiner.add(error.getDefaultMessage()));
        CustomErrorResponse responseBody = new CustomErrorResponse();
        responseBody.setTimestamp(LocalDateTime.now());
        responseBody.setStatus(HttpStatus.BAD_REQUEST.value());
        responseBody.setError("Bad Request");
        responseBody.setMessage(stringJoiner.toString());
        responseBody.setPath(request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(responseBody, headers, status);
    }
}
