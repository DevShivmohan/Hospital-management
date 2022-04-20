package com.shiv.auth.controller;

import com.shiv.auth.constants.EndpointConstant;
import com.shiv.auth.dto.UserRequestDTO;
import com.shiv.auth.exception.GenericException;
import com.shiv.auth.exception.UserAlreadyExistException;
import com.shiv.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(EndpointConstant.REST_USER)
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value =EndpointConstant.REST_ADD,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> userResponse(@RequestBody UserRequestDTO userRequestDTO) throws UserAlreadyExistException, GenericException {
        return userService.saveUser(userRequestDTO);
    }
}
