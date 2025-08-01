package com.revature.user_management.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;

/**
 * User Model - Represents a user in the system
 * This class demonstrates:
 * - MongoDB document mapping with @Document
 * - Primary key with @Id
 * - Indexed fields for better performance
 * - Lombok annotations for clean code
 * - @Data: generates getters, setters, toString, equals, hashCode
 * - @NoArgsConstructor: generates default constructor
 * - @AllArgsConstructor: generates constructor with all fields
 */
@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    @Id
    private String id;
    
    @Indexed(unique = true)
    private String email;
    
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String role;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Constructor with required fields
    public User(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.isActive = true;
    }
} 