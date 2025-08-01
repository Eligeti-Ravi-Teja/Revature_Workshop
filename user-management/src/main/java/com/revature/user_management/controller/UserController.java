package com.revature.user_management.controller;

import com.revature.user_management.dto.UserRequestDto;
import com.revature.user_management.dto.UserResponseDto;
import com.revature.user_management.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * UserController - REST API Controller for User Management
 * This class demonstrates:
 * - REST API endpoints
 * - HTTP method mappings
 * - Request/Response handling
 * - Validation
 * - Exception handling
 * - Proper HTTP status codes
 * - Dependency injection with Lombok
 * - @RequiredArgsConstructor: generates constructor for final fields
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // For development - allows all origins
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    /**
     * POST /api/users
     * Create a new user
     */
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        try {
            UserResponseDto createdUser = userService.createUser(userRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * GET /api/users
     * Get all users
     */
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
    /**
     * GET /api/users/{id}
     * Get user by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable String id) {
        Optional<UserResponseDto> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * GET /api/users/email/{email}
     * Get user by email
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDto> getUserByEmail(@PathVariable String email) {
        Optional<UserResponseDto> user = userService.getUserByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * PUT /api/users/{id}
     * Update user by ID
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable String id, 
                                                   @Valid @RequestBody UserRequestDto userRequestDto) {
        try {
            Optional<UserResponseDto> updatedUser = userService.updateUser(id, userRequestDto);
            return updatedUser.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * DELETE /api/users/{id}
     * Delete user by ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        boolean deleted = userService.deleteUser(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
    
    /**
     * GET /api/users/active
     * Get all active users
     */
    @GetMapping("/active")
    public ResponseEntity<List<UserResponseDto>> getActiveUsers() {
        List<UserResponseDto> activeUsers = userService.getActiveUsers();
        return ResponseEntity.ok(activeUsers);
    }
    
    /**
     * GET /api/users/role/{role}
     * Get users by role
     */
    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserResponseDto>> getUsersByRole(@PathVariable String role) {
        List<UserResponseDto> usersByRole = userService.getUsersByRole(role);
        return ResponseEntity.ok(usersByRole);
    }
    
    /**
     * GET /api/users/search/firstname/{firstName}
     * Search users by first name
     */
    @GetMapping("/search/firstname/{firstName}")
    public ResponseEntity<List<UserResponseDto>> searchUsersByFirstName(@PathVariable String firstName) {
        List<UserResponseDto> users = userService.searchUsersByFirstName(firstName);
        return ResponseEntity.ok(users);
    }
    
    /**
     * GET /api/users/search/lastname/{lastName}
     * Search users by last name
     */
    @GetMapping("/search/lastname/{lastName}")
    public ResponseEntity<List<UserResponseDto>> searchUsersByLastName(@PathVariable String lastName) {
        List<UserResponseDto> users = userService.searchUsersByLastName(lastName);
        return ResponseEntity.ok(users);
    }
    
    /**
     * PATCH /api/users/{id}/status
     * Activate/Deactivate user
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<UserResponseDto> setUserActiveStatus(@PathVariable String id, 
                                                            @RequestParam boolean active) {
        Optional<UserResponseDto> updatedUser = userService.setUserActiveStatus(id, active);
        return updatedUser.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * GET /api/users/exists/email/{email}
     * Check if user exists by email
     */
    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Boolean> userExistsByEmail(@PathVariable String email) {
        boolean exists = userService.userExistsByEmail(email);
        return ResponseEntity.ok(exists);
    }
    
    /**
     * GET /api/users/count/role/{role}
     * Get user count by role
     */
    @GetMapping("/count/role/{role}")
    public ResponseEntity<Long> getUserCountByRole(@PathVariable String role) {
        long count = userService.getUserCountByRole(role);
        return ResponseEntity.ok(count);
    }
    
    /**
     * GET /api/users/health
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("User Management API is running!");
    }
} 