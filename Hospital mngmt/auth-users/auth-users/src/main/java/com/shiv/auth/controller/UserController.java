package com.shiv.auth.controller;

import com.shiv.auth.constants.EndpointConstant;
import com.shiv.auth.dto.UserRequestDTO;
import com.shiv.auth.exception.GenericException;
import com.shiv.auth.exception.UserAlreadyExistException;
import com.shiv.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndpointConstant.REST_USER)
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value =EndpointConstant.REST_ADD,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addUser(@RequestBody UserRequestDTO userRequestDTO) throws UserAlreadyExistException, GenericException {
        return userService.saveUser(userRequestDTO);
    }

    @PutMapping(value =EndpointConstant.REST_UPDATE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@RequestBody UserRequestDTO userRequestDTO) {
        return userService.updateUser(userRequestDTO,userRequestDTO.getEmail());
    }

    @DeleteMapping(value =EndpointConstant.REST_DELETE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUser(@PathVariable("email") String email) {
        return userService.removeUser(email);
    }

    @GetMapping(value =EndpointConstant.REST_FETCH,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsers() {
        return userService.getUsers();
    }
}
