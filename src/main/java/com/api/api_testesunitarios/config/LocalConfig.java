package com.api.api_testesunitarios.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.api.api_testesunitarios.domain.UserModel;
import com.api.api_testesunitarios.repositories.UserRepository;

@Configuration
@Profile("local")
public class LocalConfig {
  
	@Autowired
    private UserRepository repository;
	
	@Bean
	public List<UserModel> startDB() {
	   UserModel u1 = new UserModel(null, "Victor", "victor@gmail.com", "123");
	   UserModel u2 = new UserModel(null, "Luiz", "luiz@hotmail.com", "321");
	    
	  
	   if (!repository.existsByEmail(u1.getEmail())) {
	       repository.save(u1);
	   }
	   if (!repository.existsByEmail(u2.getEmail())) {
	       repository.save(u2);
	   }

	    return List.of(u1, u2);
	}
}
