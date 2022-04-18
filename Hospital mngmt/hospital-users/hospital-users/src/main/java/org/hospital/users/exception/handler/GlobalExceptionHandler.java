package org.hospital.users.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.hospital.users.constant.APIConstants;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        JSONObject jsonObject = new JSONObject().put(APIConstants.ERROR,
                ex.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList()));
        log.error(jsonObject.toString());
        return ResponseEntity.badRequest().body(jsonObject.toString());
    }
}
