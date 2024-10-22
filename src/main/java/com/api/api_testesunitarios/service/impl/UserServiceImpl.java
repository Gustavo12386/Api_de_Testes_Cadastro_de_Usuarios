package com.api.api_testesunitarios.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.api_testesunitarios.domain.UserModel;
import com.api.api_testesunitarios.domain.dto.UserDTO;
import com.api.api_testesunitarios.repositories.UserRepository;
import com.api.api_testesunitarios.service.UserService;
import com.api.api_testesunitarios.service.exception.DataIntegratyViolationException;
import com.api.api_testesunitarios.service.exception.ObjectNotFoundException;


@Service
public class UserServiceImpl implements UserService {
   
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public Optional<UserModel> findById(Integer id) {
		Optional<UserModel> obj = repository.findById(id);
		return Optional.ofNullable(obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado")));
	}
	
	public List<UserModel> findAll(){
		return repository.findAll();
	}

	@Override
	public UserModel create(UserDTO obj) {		
		findByEmail(obj);
		return repository.save(mapper.map(obj, UserModel.class));
	}
	
	@Override
	public UserModel update(UserDTO obj) {
		findByEmail(obj);
		return repository.save(mapper.map(obj, UserModel.class));
	}
	
	private void findByEmail(UserDTO obj) {
		Optional<UserModel> user = repository.findByEmail(obj.getEmail());
		if(user.isPresent() && !user.get().getId().equals(obj.getId())) {
			throw new DataIntegratyViolationException("E-mail já cadastrado no sistema");
		}
	}

	@Override
	public void delete(Integer id) {
		findById(id);
		repository.deleteById(id);		
	}

	
	
	
}
