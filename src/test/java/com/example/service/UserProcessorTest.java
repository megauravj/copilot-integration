package com.example.service;

import com.example.dto.UserDTO;
import com.example.exception.UserProcessingException;
import com.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for UserProcessor using JUnit 5.
 * Tests filtering, mapping, sorting, and exception handling.
 */
class UserProcessorTest {
    
    private UserProcessor userProcessor;
    private List<User> testUsers;
    
    @BeforeEach
    void setUp() {
        userProcessor = new UserProcessor();
        testUsers = new ArrayList<>();
        
        // Create test data with both active and inactive users
        testUsers.add(new User(1L, "Alice Johnson", "alice@example.com", 
                LocalDateTime.of(2025, 1, 15, 10, 30, 0), true));
        testUsers.add(new User(2L, "Bob Smith", "bob@example.com", 
                LocalDateTime.of(2025, 1, 10, 14, 45, 0), false));
        testUsers.add(new User(3L, "Charlie Brown", "charlie@example.com", 
                LocalDateTime.of(2025, 1, 20, 9, 15, 0), true));
        testUsers.add(new User(4L, "Diana Prince", "diana@example.com", 
                LocalDateTime.of(2025, 1, 5, 16, 20, 0), true));
        testUsers.add(new User(5L, "Eve Wilson", "eve@example.com", 
                LocalDateTime.of(2025, 1, 12, 11, 0, 0), false));
    }
    
    @Test
    void testProcessUsers_FiltersActiveUsersOnly() throws UserProcessingException {
        List<UserDTO> result = userProcessor.processUsers(testUsers);
        
        // Should only have 3 active users (Alice, Charlie, Diana)
        assertEquals(3, result.size());
        
        // Verify all returned users are active (no Bob or Eve)
        assertTrue(result.stream()
                .map(UserDTO::getName)
                .noneMatch(name -> name.equals("Bob Smith") || name.equals("Eve Wilson")));
    }
    
    @Test
    void testProcessUsers_MapsToDTOWithFormattedDate() throws UserProcessingException {
        List<UserDTO> result = userProcessor.processUsers(testUsers);
        
        // Find Alice in results
        UserDTO alice = result.stream()
                .filter(dto -> dto.getName().equals("Alice Johnson"))
                .findFirst()
                .orElse(null);
        
        assertNotNull(alice);
        assertEquals(1L, alice.getId());
        assertEquals("alice@example.com", alice.getEmail());
        assertEquals("2025-01-15 10:30:00", alice.getFormattedCreatedDate());
    }
    
    @Test
    void testProcessUsers_SortsByNameCaseInsensitive() throws UserProcessingException {
        List<UserDTO> result = userProcessor.processUsers(testUsers);
        
        // Verify sorting by name (case-insensitive): Alice, Charlie, Diana
        assertEquals("Alice Johnson", result.get(0).getName());
        assertEquals("Charlie Brown", result.get(1).getName());
        assertEquals("Diana Prince", result.get(2).getName());
    }
    
    @Test
    void testProcessUsers_SortingIsCaseInsensitive() throws UserProcessingException {
        // Create users with mixed case names
        List<User> mixedCaseUsers = new ArrayList<>();
        mixedCaseUsers.add(new User(1L, "zoe Adams", "zoe@example.com", 
                LocalDateTime.of(2025, 1, 1, 10, 0, 0), true));
        mixedCaseUsers.add(new User(2L, "Alice Brown", "alice@example.com", 
                LocalDateTime.of(2025, 1, 1, 10, 0, 0), true));
        mixedCaseUsers.add(new User(3L, "bob Carter", "bob@example.com", 
                LocalDateTime.of(2025, 1, 1, 10, 0, 0), true));
        
        List<UserDTO> result = userProcessor.processUsers(mixedCaseUsers);
        
        // Should be sorted: Alice, bob, zoe (case-insensitive alphabetical)
        assertEquals("Alice Brown", result.get(0).getName());
        assertEquals("bob Carter", result.get(1).getName());
        assertEquals("zoe Adams", result.get(2).getName());
    }
    
    @Test
    void testProcessUsers_ThrowsExceptionForNullList() {
        assertThrows(UserProcessingException.class, () -> {
            userProcessor.processUsers(null);
        });
    }
    
    @Test
    void testProcessUsers_ThrowsExceptionForEmptyList() {
        assertThrows(UserProcessingException.class, () -> {
            userProcessor.processUsers(new ArrayList<>());
        });
    }
    
    @Test
    void testProcessUsers_ExceptionMessageIsDescriptive() {
        UserProcessingException exception = assertThrows(UserProcessingException.class, () -> {
            userProcessor.processUsers(new ArrayList<>());
        });
        
        assertEquals("User list cannot be null or empty", exception.getMessage());
    }
    
    @Test
    void testProcessUsers_AllActiveUsersWithNoInactiveUsers() throws UserProcessingException {
        List<User> allActiveUsers = new ArrayList<>();
        allActiveUsers.add(new User(1L, "User One", "user1@example.com", 
                LocalDateTime.of(2025, 1, 1, 10, 0, 0), true));
        allActiveUsers.add(new User(2L, "User Two", "user2@example.com", 
                LocalDateTime.of(2025, 1, 2, 10, 0, 0), true));
        
        List<UserDTO> result = userProcessor.processUsers(allActiveUsers);
        
        assertEquals(2, result.size());
        assertEquals("User One", result.get(0).getName());
        assertEquals("User Two", result.get(1).getName());
    }
    
    @Test
    void testProcessUsers_NoActiveUsersThrowsException() {
        List<User> inactiveUsers = new ArrayList<>();
        inactiveUsers.add(new User(1L, "Inactive User", "inactive@example.com", 
                LocalDateTime.of(2025, 1, 1, 10, 0, 0), false));
        
        // Should not throw exception - list is not empty, just no active users
        // The result should be an empty list
        assertDoesNotThrow(() -> {
            List<UserDTO> result = userProcessor.processUsers(inactiveUsers);
            assertEquals(0, result.size());
        });
    }
}
