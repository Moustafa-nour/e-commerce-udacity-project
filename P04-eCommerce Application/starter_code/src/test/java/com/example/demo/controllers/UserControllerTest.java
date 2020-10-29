package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserController userController;


    private UserRepository userRepo = mock(UserRepository.class);
    private CartRepository cartRepo = mock(CartRepository.class);
    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);
    static User user;




    @Before
    public void initial() {
        userController=new UserController();
        TestUtils.injectObject(userController, "userRepository", userRepo);
        TestUtils.injectObject(userController, "cartRepository", cartRepo);
        TestUtils.injectObject(userController, "bCryptPasswordEncoder", encoder);
    }

    @BeforeClass
    public static void setup(){
        user=new User();
        user.setUsername("Moustafa");
        user.setPassword("test123");
        user.setId(1);
    }

    @Test
    public void verify_create_user_happy_path() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("moustafa");
        request.setPassword("test123");
        request.setConfirmPassword("test123");
        ResponseEntity<User> response = userController.createUser(request);
        assertEquals(200, response.getStatusCodeValue());
        User user = response.getBody();
        assertEquals(0, user.getId());
    }
    @Test
    public void verify_create_user_sad_path() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("moustafa");
        request.setPassword("test");
        request.setConfirmPassword("test");
        ResponseEntity<User> response = userController.createUser(request);
        assertEquals(400, response.getStatusCodeValue());
        assertNull(response.getBody());

    }
    @Test
    public void verify_findById(){
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        ResponseEntity<User> response = userController.findById(1L);
        assertEquals(200,response.getStatusCodeValue());
    }

    @Test
    public void verify_findByUserName(){
        when(userRepo.findByUsername("Moustafa")).thenReturn(user);
        ResponseEntity<User> response=userController.findByUserName("Moustafa");
        assertNotNull(response);
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

}
