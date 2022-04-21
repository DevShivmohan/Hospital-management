package com.shiv.auth.service.impl;

import com.shiv.auth.constants.KeyConstant;
import com.shiv.auth.constants.ResponseConstant;
import com.shiv.auth.dao.UserRepository;
import com.shiv.auth.dto.UserAuthResponseDTO;
import com.shiv.auth.dto.UserRequestDTO;
import com.shiv.auth.dto.UserResponseDTO;
import com.shiv.auth.entity.User;
import com.shiv.auth.exception.GenericException;
import com.shiv.auth.exception.UserAlreadyExistException;
import com.shiv.auth.exception.UserBlockedException;
import com.shiv.auth.jwt.util.JwtUtil;
import com.shiv.auth.service.CustomUserDetail;
import com.shiv.auth.service.MailService;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private MailService mailService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<?> genTokenByAuthentication(String username, String password) throws UserBlockedException {
        final var finalUser=userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException(ResponseConstant.USER_NOT_FOUND));
        if(!finalUser.isActive())
            throw new UserBlockedException("User account "+finalUser.getEmail()+" has blocked");
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        }catch(BadCredentialsException badCredentialsException){
            var user=userRepository.findByEmail(username).orElseThrow(()->new BadCredentialsException("Bad credential"));
            if(user.getLoginAttemptCounter()>=3)
                user.setActive(false);
            user.setLoginAttemptCounter(user.getLoginAttemptCounter() + 1);
            userRepository.save(user);
            throw new BadCredentialsException("Bad credential");
        }
        finalUser.setLoginAttemptCounter(0);
        userRepository.save(finalUser);
        return ResponseEntity.status(HttpStatus.OK).body(new UserAuthResponseDTO(jwtUtil.generateAccessToken(finalUser),jwtUtil.generateRefreshToken(finalUser)));
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
    public ResponseEntity<?> validateToken(String token) throws GenericException, UserBlockedException {
        if(jwtUtil.isTokenExpired(token))
            throw new GenericException(ResponseConstant.ACCESS_TOKEN_HAS_EXPIRED);
        if(!jwtUtil.isAccessToken(token))
            throw new GenericException(ResponseConstant.INVALID_ACCESS_TOKEN);
        JSONObject jsonObject=new JSONObject();
        final var user=userRepository.findByEmail(jwtUtil.extractUsername(token)).orElseThrow(()->new UsernameNotFoundException(ResponseConstant.USER_NOT_FOUND));
        if(!user.isActive())
            throw new UserBlockedException("User account "+user.getEmail()+" has blocked");
        jsonObject.put(KeyConstant.NAME,user.getName());
        jsonObject.put(KeyConstant.EMAIL,user.getEmail());
        jsonObject.put(KeyConstant.MOBILE,user.getMobile());
        jsonObject.put(KeyConstant.ROLE,user.getRole());
        jsonObject.put(KeyConstant.CREATED_ON,user.getCreatedOn());
        return ResponseEntity.status(HttpStatus.OK).body(jsonObject.toString());
    }

    @Override
    public ResponseEntity<?> saveUser(UserRequestDTO userRequestDTO) throws UserAlreadyExistException, GenericException {
        final var users= userRepository.getUsers().orElseThrow(()->new UsernameNotFoundException("Nothing any users found"));
        for(var user:users)
            if(user.getEmail().equalsIgnoreCase(userRequestDTO.getEmail()))
                throw new UserAlreadyExistException("User "+userRequestDTO.getEmail()+" already exists in our database");
        if(!(userRequestDTO.getRole().equals(KeyConstant.ROLE_DOCTOR)))
            throw new GenericException("Invalid user role");
        var user=new User();
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userRequestDTO.getPassword()));
        user.setRole(userRequestDTO.getRole());
        user.setActive(true);
        user.setLoginAttemptCounter(0);
        user.setMobile(userRequestDTO.getMobile());
        user.setCreatedOn(new Date());
        if(userRepository.save(user)!=null)
            return ResponseEntity.status(HttpStatus.OK).body(new UserResponseDTO(user.getName(), user.getEmail(), user.getRole(), user.getMobile(), user.isActive(), user.getCreatedOn()));
        else
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Due to some technical problem request cann't be processed");
    }

    @Override
    public ResponseEntity<?> updateUser(UserRequestDTO userRequestDTO, String email) {
         final var user=userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException(ResponseConstant.USER_NOT_FOUND));
         user.setName(userRequestDTO.getName());
         user.setPassword(bCryptPasswordEncoder.encode(userRequestDTO.getPassword()));
         user.setMobile(userRequestDTO.getMobile());
        if(userRepository.save(user)!=null)
            return ResponseEntity.status(HttpStatus.OK).body(new UserResponseDTO(user.getName(), user.getEmail(), user.getRole(), user.getMobile(), user.isActive(), user.getCreatedOn()));
        else
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Due to some technical problem request cann't be processed");
    }

    @Override
    public ResponseEntity<?> removeUser(String email) {
        final var user=userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException(ResponseConstant.USER_NOT_FOUND));
        userRepository.deleteById(user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(new UserResponseDTO(user.getName(), user.getEmail(), user.getRole(), user.getMobile(), user.isActive(), user.getCreatedOn()));
    }

    @Override
    public ResponseEntity<?> getUsers() {
        final var users=userRepository.getUsers().orElseThrow(()->new UsernameNotFoundException("Nothing any user found"));
        List<UserResponseDTO> userResponseDTOS=new ArrayList<>();
        for(var user:users)
            userResponseDTOS.add(new UserResponseDTO(user.getName(),user.getEmail(),user.getRole(),user.getMobile(),user.isActive(),user.getCreatedOn()));
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTOS);
    }
}
