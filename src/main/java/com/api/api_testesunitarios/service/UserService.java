package com.api.api_testesunitarios.service;

import java.util.List;
import java.util.Optional;

import com.api.api_testesunitarios.domain.UserModel;
import com.api.api_testesunitarios.domain.dto.UserDTO;

public interface UserService {
	Optional<UserModel> findById(Integer id);
	List<UserModel> findAll();
	UserModel create(UserDTO obj);
	UserModel update(UserDTO obj);
	void delete(Integer id);

}
