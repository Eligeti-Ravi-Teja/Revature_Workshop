package com.revature.user_management.service.impl;

import com.revature.user_management.dto.UserRequestDto;
import com.revature.user_management.dto.UserResponseDto;
import com.revature.user_management.model.User;
import com.revature.user_management.repository.UserRepository;
import com.revature.user_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * UserServiceImpl - Business Logic Layer Implementation
 * This class demonstrates:
 * - Service layer implementation
 * - Business logic encapsulation
 * - Data transformation between layers
 * - Exception handling
 * - Dependency injection with Lombok
 * - @RequiredArgsConstructor: generates constructor for final fields
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    
    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        // Check if user already exists with the same email
        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new RuntimeException("User with email " + userRequestDto.getEmail() + " already exists");
        }
        
        // Create new user from DTO
        User user = new User();
        user.setEmail(userRequestDto.getEmail());
        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        user.setPhoneNumber(userRequestDto.getPhoneNumber());
        user.setAddress(userRequestDto.getAddress());
        user.setRole(userRequestDto.getRole());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setActive(true);
        
        // Save user to database
        User savedUser = userRepository.save(user);
        
        // Convert to response DTO and return
        return convertToResponseDto(savedUser);
    }
    
    @Override
    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<UserResponseDto> getUserById(String id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(this::convertToResponseDto);
    }
    
    @Override
    public Optional<UserResponseDto> getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(this::convertToResponseDto);
    }
    
    @Override
    public Optional<UserResponseDto> updateUser(String id, UserRequestDto userRequestDto) {
        Optional<User> existingUser = userRepository.findById(id);
        
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            
            // Update fields if provided
            if (userRequestDto.getEmail() != null && !userRequestDto.getEmail().equals(user.getEmail())) {
                // Check if new email already exists
                if (userRepository.existsByEmail(userRequestDto.getEmail())) {
                    throw new RuntimeException("User with email " + userRequestDto.getEmail() + " already exists");
                }
                user.setEmail(userRequestDto.getEmail());
            }
            
            if (userRequestDto.getFirstName() != null) {
                user.setFirstName(userRequestDto.getFirstName());
            }
            
            if (userRequestDto.getLastName() != null) {
                user.setLastName(userRequestDto.getLastName());
            }
            
            if (userRequestDto.getPhoneNumber() != null) {
                user.setPhoneNumber(userRequestDto.getPhoneNumber());
            }
            
            if (userRequestDto.getAddress() != null) {
                user.setAddress(userRequestDto.getAddress());
            }
            
            if (userRequestDto.getRole() != null) {
                user.setRole(userRequestDto.getRole());
            }
            
            user.setUpdatedAt(LocalDateTime.now());
            
            User updatedUser = userRepository.save(user);
            return Optional.of(convertToResponseDto(updatedUser));
        }
        
        return Optional.empty();
    }
    
    @Override
    public boolean deleteUser(String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    @Override
    public List<UserResponseDto> getActiveUsers() {
        List<User> activeUsers = userRepository.findByIsActiveTrue();
        return activeUsers.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<UserResponseDto> getUsersByRole(String role) {
        List<User> usersByRole = userRepository.findByRole(role);
        return usersByRole.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<UserResponseDto> searchUsersByFirstName(String firstName) {
        List<User> users = userRepository.findByFirstNameIgnoreCase(firstName);
        return users.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<UserResponseDto> searchUsersByLastName(String lastName) {
        List<User> users = userRepository.findByLastNameIgnoreCase(lastName);
        return users.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<UserResponseDto> setUserActiveStatus(String id, boolean active) {
        Optional<User> user = userRepository.findById(id);
        
        if (user.isPresent()) {
            User existingUser = user.get();
            existingUser.setActive(active);
            existingUser.setUpdatedAt(LocalDateTime.now());
            
            User updatedUser = userRepository.save(existingUser);
            return Optional.of(convertToResponseDto(updatedUser));
        }
        
        return Optional.empty();
    }
    
    @Override
    public boolean userExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    @Override
    public long getUserCountByRole(String role) {
        return userRepository.countByRole(role);
    }
    
    /**
     * Helper method to convert User entity to UserResponseDto
     * This demonstrates data transformation between layers
     */
    private UserResponseDto convertToResponseDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getAddress(),
                user.getRole(),
                user.isActive(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
} 