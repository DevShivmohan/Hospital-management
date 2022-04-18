package com.shiv.auth.exception.handler;

import com.shiv.auth.exception.GenericException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> usernameNotFound(UsernameNotFoundException usernameNotFoundException){
        log.error(usernameNotFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(usernameNotFoundException.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentials(BadCredentialsException badCredentialsException){
        log.error(badCredentialsException.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(badCredentialsException.getMessage());
    }

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<?> handleGenricException(GenericException genericException){
        log.error(genericException.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(genericException.getMessage());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> validateToken(ExpiredJwtException expiredJwtException){
        log.error(expiredJwtException.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Token has expired");
    }
}
