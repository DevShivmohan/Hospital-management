package com.shiv.auth.controller;

import com.shiv.auth.constants.EndpointConstant;
import com.shiv.auth.dto.RefreshTokenDTO;
import com.shiv.auth.dto.UserAuthRequestDTO;
import com.shiv.auth.exception.GenericException;
import com.shiv.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndpointConstant.REST_AUTH)
public class UserAuthenticationController {

    @Autowired
    private UserService userService;

    @PostMapping(value = EndpointConstant.REST_LOGIN,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> userAuthentication(@RequestBody UserAuthRequestDTO userAuthRequestDTO){
        return userService.genTokenByAuthentication(userAuthRequestDTO.getUsername(),userAuthRequestDTO.getPassword());
    }

    @PostMapping(value = EndpointConstant.REST_GEN_REFRESH_TOKEN,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> genByRefreshToken(@RequestBody RefreshTokenDTO refreshTokenDTO){
        return userService.genTokenByRefreshToken(refreshTokenDTO.getRefreshToken());
    }

    @GetMapping(value = EndpointConstant.REST_VALIDATE_TOKEN,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> validateToken(@RequestBody RefreshTokenDTO refreshTokenDTO) throws GenericException {
        return userService.validateToken(refreshTokenDTO.getAccessToken());
    }
}
