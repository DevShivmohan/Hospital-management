package com.shiv.auth.controller;

import com.shiv.auth.constants.EndpointConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(EndpointConstant.REST_USER)
public class UserController {

    @RequestMapping("/hi")
    public ResponseEntity<?> userResponse(){
        return ResponseEntity.status(HttpStatus.OK).body("Welcome in spring boot");
    }
}
