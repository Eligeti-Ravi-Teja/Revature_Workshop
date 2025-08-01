package com.revature.user_management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserRequestDto - Data Transfer Object for incoming user requests
 * This class demonstrates:
 * - Input validation with @Valid annotations
 * - Separation of concerns (API layer vs Model layer)
 * - Data transformation between layers
 * - Lombok annotations for clean code
 * - @Data: generates getters, setters, toString, equals, hashCode
 * - @NoArgsConstructor: generates default constructor
 * - @AllArgsConstructor: generates constructor with all fields
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;
    
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;
    
    @Size(max = 15, message = "Phone number must be less than 15 characters")
    private String phoneNumber;
    
    @Size(max = 200, message = "Address must be less than 200 characters")
    private String address;
    
    private String role;
    
    // Constructor with required fields
    public UserRequestDto(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }
} 