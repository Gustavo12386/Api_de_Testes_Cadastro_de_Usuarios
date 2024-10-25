package com.api.api_testesunitarios.services;

import java.util.List;
import java.util.Optional;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.api.api_testesunitarios.domain.UserModel;
import com.api.api_testesunitarios.domain.dto.UserDTO;
import com.api.api_testesunitarios.repositories.UserRepository;
import com.api.api_testesunitarios.service.exception.DataIntegratyViolationException;
import com.api.api_testesunitarios.service.exception.ObjectNotFoundException;
import com.api.api_testesunitarios.service.impl.UserServiceImpl;

@SpringBootTest
public class UserServiceImplTest {
   
	private static final Integer ID = 1;
	private static final Integer INDEX   = 0;	
	private static final String NAME = "Gustavo";
	private static final String EMAIL = "calderarogustavo@gmail.com";
	private static final String PASSWORD = "123";
	
	@InjectMocks
	private UserServiceImpl service;
	
	@Mock
	private UserRepository repository;
	
	@Mock
	private ModelMapper mapper;
	
	private UserModel user;
	private UserDTO userDTO;
	private Optional<UserModel> optionalUser;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		startUser();
	}
	
	@Test
	void whenFindByIdThenReturnAnUserInstance() {		
		Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(optionalUser);
		
		UserModel response = service.findById(ID);
		
		Assertions.assertNotNull(response);
		Assertions.assertEquals(UserModel.class, response.getClass());
		Assertions.assertEquals(ID, response.getId());
		Assertions.assertEquals(NAME, response.getName());
		Assertions.assertEquals(EMAIL, response.getEmail());
		
	}
	
	@Test
	void whenFindByIdThenReturnAnObjectNotFoundException() {
	   Mockito.when(repository.findById(Mockito.anyInt())).thenThrow(new ObjectNotFoundException("Objeto não encontrado"));	
	   try {
		 service.findById(ID);  
	   } catch(Exception ex) {
		   Assertions.assertEquals(ObjectNotFoundException.class, ex.getClass());
		   Assertions.assertEquals("Objeto não encontrado", ex.getMessage());
	   }
	}
	
	@Test
	void whenFindAllThenReturnAnListOfUsers() {
		Mockito.when(repository.findAll()).thenReturn(List.of(user));
		
		List<UserModel> response = service.findAll();
		
		Assertions.assertNotNull(response);
		Assertions.assertEquals(1, response.size());
		Assertions.assertEquals(UserModel.class, response.get(INDEX).getClass());
		
		Assertions.assertEquals(ID, response.get(INDEX).getId());
		Assertions.assertEquals(NAME, response.get(INDEX).getName());
		Assertions.assertEquals(EMAIL, response.get(INDEX).getEmail());
		Assertions.assertEquals(PASSWORD, response.get(INDEX).getPassword());
		
	}
	
	@Test
	void whenCreateThenReturnSuccess() {
		Mockito.when(repository.save(Mockito.any())).thenReturn(user);
		
		UserModel response = service.create(userDTO);
		
		Assertions.assertNotNull(response);
		Assertions.assertEquals(UserModel.class, response.getClass());		
		Assertions.assertEquals(ID, response.getId());
		Assertions.assertEquals(NAME, response.getName());
		Assertions.assertEquals(EMAIL, response.getEmail());
		Assertions.assertEquals(PASSWORD, response.getPassword());
	}
	
	@Test
	    void whenCreateThenReturnAnDataIntegrityViolationException() {
	        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(optionalUser);

	        try{
	            optionalUser.get().setId(2);
	            service.create(userDTO);
	        } catch (Exception ex) {
	            Assertions.assertEquals(DataIntegratyViolationException.class, ex.getClass());
	            Assertions.assertEquals("email_ja_cadastrado_no_sistema", ex.getMessage());
	        }
	 }
	
	@Test
	void whenUpdateThenReturnSuccess() {
		Mockito.when(repository.save(Mockito.any())).thenReturn(user);
		
		UserModel response = service.update(userDTO);
		
		Assertions.assertNotNull(response);
		Assertions.assertEquals(UserModel.class, response.getClass());		
		Assertions.assertEquals(ID, response.getId());
		Assertions.assertEquals(NAME, response.getName());
		Assertions.assertEquals(EMAIL, response.getEmail());
		Assertions.assertEquals(PASSWORD, response.getPassword());
	}	
	
	@Test
    void whenUpdateThenReturnAnDataIntegrityViolationException() {
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(optionalUser);

        try{
            optionalUser.get().setId(2);
            service.create(userDTO);
        } catch (Exception ex) {
            Assertions.assertEquals(DataIntegratyViolationException.class, ex.getClass());
            Assertions.assertEquals("email_ja_cadastrado_no_sistema", ex.getMessage());
        }
    }	
	
	@Test
	void deleteWithSuccess() {
	  Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(optionalUser);
	  Mockito.doNothing().when(repository).deleteById(Mockito.anyInt());
	  service.delete(ID);
	  Mockito.verify(repository, Mockito.times(1)).deleteById(Mockito.anyInt());
	}
	
	@Test
	  void whenDeleteThenReturnObjectNotFoundException() {
	     Mockito.when(repository.findById(Mockito.anyInt()))
	          .thenThrow(new ObjectNotFoundException("Objeto não encontrado"));
	     try {
	       service.delete(ID);
	      } catch (Exception ex) {
	        Assertions.assertEquals(ObjectNotFoundException.class, ex.getClass());
	        Assertions.assertEquals("Objeto não encontrado", ex.getMessage());
	    }
	  }
	 
	private void startUser() {
		user = new UserModel(ID, NAME, EMAIL, PASSWORD);
		userDTO = new UserDTO(ID, NAME, EMAIL,PASSWORD );
		optionalUser = Optional.of(new UserModel(ID, NAME, EMAIL, PASSWORD ));
	}
	
	
	
	
	
}
