package com.lucassilva.simplified_picpay.authorization;

import com.lucassilva.simplified_picpay.notification.NotificationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthorizationExceptionHandler {
    @ExceptionHandler(UnauthorizedTransactionException.class)
    public ResponseEntity<Object> handle(UnauthorizedTransactionException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
