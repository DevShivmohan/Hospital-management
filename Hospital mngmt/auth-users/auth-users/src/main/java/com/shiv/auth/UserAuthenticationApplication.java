package com.shiv.auth;

import com.shiv.auth.dao.UserRepository;
import com.shiv.auth.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class UserAuthenticationApplication {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private  BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostConstruct
	public  void d(){
		List<User> users= List.of(new User(1L,"Shiv","shiv1@gmail.com",bCryptPasswordEncoder.encode("pass1"),"ROLE_USER",true,0,"7080713024",new Date()),
				new User(2L,"Mohan","shiv2@gmail.com",bCryptPasswordEncoder.encode("pass2"),"ROLE_PATIENT",true,0,"7080713024",new Date()));
	userRepository.saveAll(users);
	}


	public static void main(String[] args) {
		SpringApplication.run(UserAuthenticationApplication.class, args);
	}

}
