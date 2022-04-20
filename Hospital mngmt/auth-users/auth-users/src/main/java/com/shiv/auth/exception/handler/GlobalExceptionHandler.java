package com.shiv.auth.exception.handler;

import com.shiv.auth.constants.ResponseConstant;
import com.shiv.auth.exception.GenericException;
import com.shiv.auth.exception.UserAlreadyExistException;
import com.shiv.auth.exception.UserBlockedException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.mail.AuthenticationFailedException;

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
    public ResponseEntity<?> handleGenericException(GenericException genericException){
        log.error(genericException.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(genericException.getMessage());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> validateToken(ExpiredJwtException expiredJwtException){
        log.error(expiredJwtException.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseConstant.TOKEN_EXPIRED);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<?> handleUserExist(UserAlreadyExistException userAlreadyExistException){
        log.error(userAlreadyExistException.getMessage());
        return ResponseEntity.status(HttpStatus.IM_USED).body(userAlreadyExistException.getMessage());
    }

    @ExceptionHandler(UserBlockedException.class)
    public ResponseEntity<?> handleUserBlocked(UserBlockedException userBlockedException){
        log.error(userBlockedException.getMessage());
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(userBlockedException.getMessage());
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<?> handleMailAuthFailed(AuthenticationFailedException authenticationFailedException){
        log.error(authenticationFailedException.getMessage());
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(authenticationFailedException.getMessage());
    }
}
