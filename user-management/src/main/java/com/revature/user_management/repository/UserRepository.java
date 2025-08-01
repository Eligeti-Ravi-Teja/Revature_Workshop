package com.revature.user_management.repository;

import com.revature.user_management.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * UserRepository - Data Access Layer for User entity
 * This interface demonstrates:
 * - Spring Data MongoDB repository
 * - Custom query methods
 * - CRUD operations inheritance
 * - Repository pattern implementation
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    
    /**
     * Find user by email (unique field)
     * Spring Data automatically creates this method based on method name
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Find all active users
     * Spring Data automatically creates this method based on method name
     */
    List<User> findByIsActiveTrue();
    
    /**
     * Find users by role
     * Spring Data automatically creates this method based on method name
     */
    List<User> findByRole(String role);
    
    /**
     * Find users by first name (case-insensitive)
     * Spring Data automatically creates this method based on method name
     */
    List<User> findByFirstNameIgnoreCase(String firstName);
    
    /**
     * Find users by last name (case-insensitive)
     * Spring Data automatically creates this method based on method name
     */
    List<User> findByLastNameIgnoreCase(String lastName);
    
    /**
     * Custom query to find users by email pattern
     * This demonstrates custom MongoDB queries
     */
    @Query("{'email': {$regex: ?0, $options: 'i'}}")
    List<User> findByEmailPattern(String emailPattern);
    
    /**
     * Custom query to find users created after a specific date
     * This demonstrates custom MongoDB queries with date filtering
     */
    @Query("{'createdAt': {$gte: ?0}}")
    List<User> findByCreatedAtAfter(java.time.LocalDateTime date);
    
    /**
     * Check if user exists by email
     * Spring Data automatically creates this method based on method name
     */
    boolean existsByEmail(String email);
    
    /**
     * Count users by role
     * Spring Data automatically creates this method based on method name
     */
    long countByRole(String role);
} 