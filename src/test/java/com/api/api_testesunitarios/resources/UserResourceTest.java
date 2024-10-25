package com.api.api_testesunitarios.resources;


import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import com.api.api_testesunitarios.domain.UserModel;
import com.api.api_testesunitarios.domain.dto.UserDTO;
import com.api.api_testesunitarios.resource.UserResource;
import com.api.api_testesunitarios.service.impl.UserServiceImpl;

@SpringBootTest
public class UserResourceTest {
	
  private static final Integer ID = 1;
  private static final Integer INDEX   = 0;	
  private static final String NAME = "Gustavo";
  private static final String EMAIL = "calderarogustavo@gmail.com";
  private static final String PASSWORD = "123";  
	
   @InjectMocks
   private UserResource resource;
   
   @Mock
   private UserServiceImpl service;
   
   @Mock
   private ModelMapper mapper;
   
   private UserModel user;
   private UserDTO userDTO;
   
   @BeforeEach
   void setUp() {
	   MockitoAnnotations.openMocks(this);	 
	   startUser();
   }
   
   @Test
   void whenFindByIdThenReturnSuccess() {
	  Mockito.when(service.findById(Mockito.anyInt())).thenReturn(user);
	  Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(userDTO);
	  
	  ResponseEntity<UserDTO> response = resource.findById(ID);
	  
	  Assertions.assertNotNull(response);
	  Assertions.assertNotNull(response.getBody());
	  Assertions.assertEquals(ResponseEntity.class, response.getClass());
	  Assertions.assertEquals(UserDTO.class, response.getBody().getClass());
	  
	  Assertions.assertEquals(ID, response.getBody().getId());
	  Assertions.assertEquals(NAME, response.getBody().getName());
	  Assertions.assertEquals(EMAIL, response.getBody().getEmail());
	  Assertions.assertEquals(PASSWORD, response.getBody().getPassword());
   }
   
   @Test
   void whenFindAllThenReturnAListOfUserDTO() {
	   Mockito.when(service.findAll()).thenReturn(List.of(user));
	   Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(userDTO);
	   
	   ResponseEntity<List<UserDTO>> response = resource.findAll();
	   
	   Assertions.assertNotNull(response);
	   Assertions.assertNotNull(response.getBody());
	   Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
	   Assertions.assertEquals(ResponseEntity.class, response.getClass());
	   Assertions.assertEquals(ArrayList.class, response.getBody().getClass());
	   Assertions.assertEquals(UserDTO.class, response.getBody().get(INDEX).getClass());
	   
	   Assertions.assertEquals(ID, response.getBody().get(INDEX).getId());
	   Assertions.assertEquals(NAME, response.getBody().get(INDEX).getName());
	   Assertions.assertEquals(EMAIL, response.getBody().get(INDEX).getEmail());
	   Assertions.assertEquals(PASSWORD, response.getBody().get(INDEX).getPassword());
	   
   }
   
   @Test
   void whenCreateThenReturnCreated(){
       Mockito.when(service.create(Mockito.any())).thenReturn(user);

       ResponseEntity<UserDTO> response = resource.create(userDTO);

       Assertions.assertEquals(ResponseEntity.class, response.getClass());
       Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
       Assertions.assertNotNull(response.getHeaders().get("Location"));
   }
   
   @Test
   void whenUpdateThenReturnCreated() {
	   Mockito.when(service.update(userDTO)).thenReturn(user);
	   Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(userDTO);
	   
	   ResponseEntity<UserDTO> response = resource.update(ID, userDTO);
	   
	   Assertions.assertNotNull(response);
	   Assertions.assertNotNull(response.getBody());
	   Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
	   Assertions.assertEquals(ResponseEntity.class, response.getClass());	   
	   Assertions.assertEquals(UserDTO.class, response.getBody().getClass());
	   
	   Assertions.assertEquals(ID, response.getBody().getId());
	   Assertions.assertEquals(NAME, response.getBody().getName());
	   Assertions.assertEquals(EMAIL, response.getBody().getEmail());	   
   }
   
   @Test
   void whenDeleteThenReturnSuccess() {
	   Mockito.doNothing().when(service).delete(Mockito.anyInt());
	   
	   ResponseEntity<UserDTO> response = resource.delete(ID);
	   
	   Assertions.assertNotNull(response);
	   Assertions.assertEquals(ResponseEntity.class, response.getClass());
	   Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
       Mockito.verify(service, Mockito.times(1)).delete(Mockito.anyInt());	 
       
   }
   
   
   
   private void startUser() {
		user = new UserModel(ID, NAME, EMAIL, PASSWORD);
		userDTO = new UserDTO(ID, NAME, EMAIL,PASSWORD );		
   }

}
