package com.shiv.auth.service.impl;

import com.shiv.auth.constants.KeyConstant;
import com.shiv.auth.constants.ResponseConstant;
import com.shiv.auth.dao.UserRepository;
import com.shiv.auth.dto.UserAuthResponseDTO;
import com.shiv.auth.exception.GenericException;
import com.shiv.auth.jwt.util.JwtUtil;
import com.shiv.auth.service.CustomUserDetail;
import com.shiv.auth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<?> genTokenByAuthentication(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        final var user=userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException(ResponseConstant.USER_NOT_FOUND));
        return ResponseEntity.status(HttpStatus.OK).body(new UserAuthResponseDTO(jwtUtil.generateAccessToken(user),jwtUtil.generateRefreshToken(user)));
    }

    @Override
    public ResponseEntity<?> genTokenByRefreshToken(String refreshToken) {
        var user=userRepository.findByEmail(jwtUtil.extractUsername(refreshToken)).orElseThrow(()->new UsernameNotFoundException(ResponseConstant.USER_NOT_FOUND));
        if(!jwtUtil.validateToken(refreshToken, new CustomUserDetail(user)))
            throw new BadCredentialsException(ResponseConstant.REFRESH_TOKEN_HAS_EXPIRED);
        if(jwtUtil.isAccessToken(refreshToken))
            throw new BadCredentialsException(ResponseConstant.INVALID_REFRESH_TOKEN);
        return ResponseEntity.status(HttpStatus.OK).body(new UserAuthResponseDTO(jwtUtil.generateAccessToken(user), jwtUtil.generateRefreshToken(user)));
    }

    @Override
    public ResponseEntity<?> validateToken(String token) throws GenericException {
        if(jwtUtil.isTokenExpired(token))
            throw new GenericException(ResponseConstant.ACCESS_TOKEN_HAS_EXPIRED);
        if(!jwtUtil.isAccessToken(token))
            throw new GenericException(ResponseConstant.INVALID_ACCESS_TOKEN);
        JSONObject jsonObject=new JSONObject();
        final var user=userRepository.findByEmail(jwtUtil.extractUsername(token)).orElseThrow(()->new UsernameNotFoundException(ResponseConstant.USER_NOT_FOUND));
        jsonObject.put(KeyConstant.NAME,user.getName());
        jsonObject.put(KeyConstant.EMAIL,user.getEmail());
        jsonObject.put(KeyConstant.MOBILE,user.getMobile());
        jsonObject.put(KeyConstant.ROLE,user.getRole());
        jsonObject.put(KeyConstant.CREATED_ON,user.getCreatedOn());
        return ResponseEntity.status(HttpStatus.OK).body(jsonObject.toString());
    }
}
