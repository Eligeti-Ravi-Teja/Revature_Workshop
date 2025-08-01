package com.revature.user_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * UserResponseDto - Data Transfer Object for outgoing user responses
 * This class demonstrates:
 * - Data transformation for API responses
 * - Hiding sensitive information
 * - Consistent response format
 * - Lombok annotations for clean code
 * - @Data: generates getters, setters, toString, equals, hashCode
 * - @NoArgsConstructor: generates default constructor
 * - @AllArgsConstructor: generates constructor with all fields
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String role;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 