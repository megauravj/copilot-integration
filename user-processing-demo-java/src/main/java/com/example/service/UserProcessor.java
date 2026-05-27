package com.example.service;

import com.example.dto.UserDTO;
import com.example.exception.UserProcessingException;
import com.example.model.User;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for processing User objects using streams and functional interfaces.
 * Filters active users, maps to UserDTO, and sorts by name.
 */
public class UserProcessor {
    
    private static final DateTimeFormatter DATE_FORMATTER = 
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    /**
     * Processes a list of users by filtering active users, mapping to UserDTO,
     * and sorting by name (case-insensitive).
     *
     * @param users the list of users to process
     * @return a sorted list of UserDTOs for active users
     * @throws UserProcessingException if the input list is null or empty
     */
    public List<UserDTO> processUsers(List<User> users) throws UserProcessingException {
        if (users == null || users.isEmpty()) {
            throw new UserProcessingException("User list cannot be null or empty");
        }
        
        return users.stream()
                // Step 1: Filter active users only
                .filter(User::isActive)
                // Step 2: Map to UserDTO with formatted date
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getCreatedDate().format(DATE_FORMATTER)
                ))
                // Step 3: Sort by name (case-insensitive)
                .sorted((dto1, dto2) -> 
                        dto1.getName().compareToIgnoreCase(dto2.getName())
                )
                // Step 4: Collect to List
                .collect(Collectors.toList());
    }
}
