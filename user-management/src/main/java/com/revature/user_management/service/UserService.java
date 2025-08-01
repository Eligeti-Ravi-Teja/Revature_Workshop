package com.revature.user_management.service;

import com.revature.user_management.dto.UserRequestDto;
import com.revature.user_management.dto.UserResponseDto;
import com.revature.user_management.model.User;

import java.util.List;
import java.util.Optional;

/**
 * UserService - Business Logic Layer Interface
 * This interface demonstrates:
 * - Service layer pattern
 * - Business logic separation
 * - Interface-based design for testability
 * - CRUD operations definition
 */
public interface UserService {
    
    /**
     * Create a new user
     * @param userRequestDto - User data from request
     * @return UserResponseDto - Created user data
     */
    UserResponseDto createUser(UserRequestDto userRequestDto);
    
    /**
     * Get all users
     * @return List of all users
     */
    List<UserResponseDto> getAllUsers();
    
    /**
     * Get user by ID
     * @param id - User ID
     * @return Optional containing user if found
     */
    Optional<UserResponseDto> getUserById(String id);
    
    /**
     * Get user by email
     * @param email - User email
     * @return Optional containing user if found
     */
    Optional<UserResponseDto> getUserByEmail(String email);
    
    /**
     * Update user by ID
     * @param id - User ID
     * @param userRequestDto - Updated user data
     * @return Optional containing updated user if found
     */
    Optional<UserResponseDto> updateUser(String id, UserRequestDto userRequestDto);
    
    /**
     * Delete user by ID
     * @param id - User ID
     * @return true if user was deleted, false if not found
     */
    boolean deleteUser(String id);
    
    /**
     * Get all active users
     * @return List of active users
     */
    List<UserResponseDto> getActiveUsers();
    
    /**
     * Get users by role
     * @param role - User role
     * @return List of users with specified role
     */
    List<UserResponseDto> getUsersByRole(String role);
    
    /**
     * Search users by first name
     * @param firstName - First name to search
     * @return List of users matching the first name
     */
    List<UserResponseDto> searchUsersByFirstName(String firstName);
    
    /**
     * Search users by last name
     * @param lastName - Last name to search
     * @return List of users matching the last name
     */
    List<UserResponseDto> searchUsersByLastName(String lastName);
    
    /**
     * Activate/Deactivate user
     * @param id - User ID
     * @param active - Active status
     * @return Optional containing updated user if found
     */
    Optional<UserResponseDto> setUserActiveStatus(String id, boolean active);
    
    /**
     * Check if user exists by email
     * @param email - User email
     * @return true if user exists, false otherwise
     */
    boolean userExistsByEmail(String email);
    
    /**
     * Get user count by role
     * @param role - User role
     * @return Number of users with specified role
     */
    long getUserCountByRole(String role);
} 