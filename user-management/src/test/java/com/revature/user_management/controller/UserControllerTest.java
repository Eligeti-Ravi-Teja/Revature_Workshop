package com.revature.user_management.controller;

import com.revature.user_management.dto.UserRequestDto;
import com.revature.user_management.dto.UserResponseDto;
import com.revature.user_management.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * UserControllerTest - Unit Tests for UserController
 * This class demonstrates:
 * - Unit testing with JUnit 5
 * - Mockito for mocking dependencies
 * - Testing REST controller endpoints
 * - Assertion methods
 * - Test organization and naming
 */
class UserControllerTest {
    
    @Mock
    private UserService userService;
    
    @InjectMocks
    private UserController userController;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testCreateUser_Success() {
        // Arrange
        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setEmail("test@example.com");
        requestDto.setFirstName("John");
        requestDto.setLastName("Doe");
        
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId("123");
        responseDto.setEmail("test@example.com");
        responseDto.setFirstName("John");
        responseDto.setLastName("Doe");
        responseDto.setCreatedAt(LocalDateTime.now());
        
        when(userService.createUser(any(UserRequestDto.class))).thenReturn(responseDto);
        
        // Act
        ResponseEntity<UserResponseDto> response = userController.createUser(requestDto);
        
        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("123", response.getBody().getId());
        assertEquals("test@example.com", response.getBody().getEmail());
        
        verify(userService, times(1)).createUser(any(UserRequestDto.class));
    }
    
    @Test
    void testCreateUser_ThrowsException() {
        // Arrange
        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setEmail("test@example.com");
        requestDto.setFirstName("John");
        requestDto.setLastName("Doe");
        
        when(userService.createUser(any(UserRequestDto.class)))
                .thenThrow(new RuntimeException("User already exists"));
        
        // Act
        ResponseEntity<UserResponseDto> response = userController.createUser(requestDto);
        
        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        
        verify(userService, times(1)).createUser(any(UserRequestDto.class));
    }
    
    @Test
    void testGetAllUsers_Success() {
        // Arrange
        UserResponseDto user1 = new UserResponseDto();
        user1.setId("1");
        user1.setEmail("user1@example.com");
        user1.setFirstName("John");
        user1.setLastName("Doe");
        
        UserResponseDto user2 = new UserResponseDto();
        user2.setId("2");
        user2.setEmail("user2@example.com");
        user2.setFirstName("Jane");
        user2.setLastName("Smith");
        
        List<UserResponseDto> users = Arrays.asList(user1, user2);
        
        when(userService.getAllUsers()).thenReturn(users);
        
        // Act
        ResponseEntity<List<UserResponseDto>> response = userController.getAllUsers();
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("user1@example.com", response.getBody().get(0).getEmail());
        assertEquals("user2@example.com", response.getBody().get(1).getEmail());
        
        verify(userService, times(1)).getAllUsers();
    }
    
    @Test
    void testGetUserById_Success() {
        // Arrange
        String userId = "123";
        UserResponseDto user = new UserResponseDto();
        user.setId(userId);
        user.setEmail("test@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        
        when(userService.getUserById(userId)).thenReturn(Optional.of(user));
        
        // Act
        ResponseEntity<UserResponseDto> response = userController.getUserById(userId);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(userId, response.getBody().getId());
        
        verify(userService, times(1)).getUserById(userId);
    }
    
    @Test
    void testGetUserById_NotFound() {
        // Arrange
        String userId = "999";
        when(userService.getUserById(userId)).thenReturn(Optional.empty());
        
        // Act
        ResponseEntity<UserResponseDto> response = userController.getUserById(userId);
        
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        
        verify(userService, times(1)).getUserById(userId);
    }
    
    @Test
    void testDeleteUser_Success() {
        // Arrange
        String userId = "123";
        when(userService.deleteUser(userId)).thenReturn(true);
        
        // Act
        ResponseEntity<Void> response = userController.deleteUser(userId);
        
        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        
        verify(userService, times(1)).deleteUser(userId);
    }
    
    @Test
    void testDeleteUser_NotFound() {
        // Arrange
        String userId = "999";
        when(userService.deleteUser(userId)).thenReturn(false);
        
        // Act
        ResponseEntity<Void> response = userController.deleteUser(userId);
        
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        
        verify(userService, times(1)).deleteUser(userId);
    }
    
    @Test
    void testHealthCheck() {
        // Act
        ResponseEntity<String> response = userController.health();
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User Management API is running!", response.getBody());
    }
} 