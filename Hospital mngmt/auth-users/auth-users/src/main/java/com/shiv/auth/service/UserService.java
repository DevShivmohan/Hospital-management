package com.shiv.auth.service;

import com.shiv.auth.exception.GenericException;
import org.springframework.http.ResponseEntity;

public interface UserService {
    public ResponseEntity<?> genTokenByAuthentication(String username,String password);
    public ResponseEntity<?> genTokenByRefreshToken(String refreshToken);
    public ResponseEntity<?> validateToken(String token) throws GenericException;
}
