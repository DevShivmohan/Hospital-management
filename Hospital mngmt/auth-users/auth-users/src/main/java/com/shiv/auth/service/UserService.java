package com.shiv.auth.service;

import com.shiv.auth.dto.UserRequestDTO;
import com.shiv.auth.exception.GenericException;
import com.shiv.auth.exception.UserAlreadyExistException;
import com.shiv.auth.exception.UserBlockedException;
import org.springframework.http.ResponseEntity;

public interface UserService {
    public ResponseEntity<?> genTokenByAuthentication(String username,String password) throws UserBlockedException;
    public ResponseEntity<?> genTokenByRefreshToken(String refreshToken);
    public ResponseEntity<?> validateToken(String token) throws GenericException, UserBlockedException;
    public ResponseEntity<?> saveUser(UserRequestDTO userRequestDTO) throws UserAlreadyExistException, GenericException;
    public ResponseEntity<?> updateUser(UserRequestDTO userRequestDTO,String email);
    public ResponseEntity<?> removeUser(String email);
    public ResponseEntity<?> getUsers();
}
