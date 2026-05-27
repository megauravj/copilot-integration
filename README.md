# Copilot Integration - User Processing Demo

A Java 11 demonstration project showcasing stream-based user processing with custom DTOs, exception handling, and comprehensive JUnit 5 tests.

## Project Overview

This project demonstrates best practices for:
- **Java Streams API**: Functional programming with method references and lambda expressions
- **Data Transfer Objects (DTOs)**: Mapping domain models to serializable objects
- **Custom Exceptions**: Proper error handling with meaningful messages
- **JUnit 5**: Modern unit testing with parametrized tests and assertions

## Features

✅ **Filter Active Users**: Streams filter only users with `active = true`  
✅ **Map to UserDTO**: Converts User objects to UserDTO with formatted dates  
✅ **Case-Insensitive Sorting**: Sorts by user name alphabetically  
✅ **Custom Exception Handling**: Throws `UserProcessingException` for invalid input  
✅ **Comprehensive Tests**: 8 test cases covering all scenarios  

## Project Structure

```
src/
├── main/
│   └── java/
│       └── com/example/
│           ├── exception/
│           │   └── UserProcessingException.java
│           ├── model/
│           │   └── User.java
│           ├── dto/
│           │   └── UserDTO.java
│           └── service/
│               └── UserProcessor.java
└── test/
    └── java/
        └── com/example/
            └── service/
                └── UserProcessorTest.java
```

## Classes

### User Model
- **Properties**: `id` (long), `name` (String), `email` (String), `createdDate` (LocalDateTime), `active` (boolean)
- **Purpose**: Domain model representing a user entity

### UserDTO
- **Properties**: `id` (long), `name` (String), `email` (String), `formattedCreatedDate` (String)
- **Purpose**: Data transfer object with pre-formatted date string

### UserProcessingException
- **Extends**: Exception
- **Purpose**: Custom exception for invalid or empty user lists

### UserProcessor
- **Method**: `processUsers(List<User>)` → `List<UserDTO>`
- **Operations**:
  1. Filters active users only
  2. Maps to UserDTO with formatted date (yyyy-MM-dd HH:mm:ss)
  3. Sorts by name (case-insensitive)
  4. Collects to List
  5. Throws exception if input is null or empty

## Usage Example

```java
List<User> users = new ArrayList<>();
users.add(new User(1L, "John Doe", "john@example.com", 
    LocalDateTime.now(), true));
users.add(new User(2L, "Jane Smith", "jane@example.com", 
    LocalDateTime.now(), false));

UserProcessor processor = new UserProcessor();
try {
    List<UserDTO> activeUsers = processor.processUsers(users);
    activeUsers.forEach(System.out::println);
} catch (UserProcessingException e) {
    System.err.println("Error: " + e.getMessage());
}
```

## Building the Project

### Prerequisites
- Java 11 or higher
- Gradle 7.0 or higher

### Build
```bash
./gradlew build
```

### Run Tests
```bash
./gradlew test
```

### Run Tests with Output
```bash
./gradlew test --info
```

## Test Coverage

The test suite (`UserProcessorTest`) includes:

1. **testProcessUsers_FiltersActiveUsersOnly** - Verifies filtering works
2. **testProcessUsers_MapsToDTOWithFormattedDate** - Verifies DTO mapping and date formatting
3. **testProcessUsers_SortsByNameCaseInsensitive** - Verifies sorting order
4. **testProcessUsers_SortingIsCaseInsensitive** - Verifies case-insensitive sorting
5. **testProcessUsers_ThrowsExceptionForNullList** - Verifies null handling
6. **testProcessUsers_ThrowsExceptionForEmptyList** - Verifies empty list handling
7. **testProcessUsers_ExceptionMessageIsDescriptive** - Verifies exception message
8. **testProcessUsers_AllActiveUsersWithNoInactiveUsers** - Verifies all-active scenario
9. **testProcessUsers_NoActiveUsersThrowsException** - Verifies no-active-users scenario

## Stream Pipeline Breakdown

```java
return users.stream()
    .filter(User::isActive)                              // Step 1: Filter
    .map(user -> new UserDTO(...))                       // Step 2: Transform
    .sorted((dto1, dto2) -> 
        dto1.getName().compareToIgnoreCase(dto2.getName())) // Step 3: Sort
    .collect(Collectors.toList());                       // Step 4: Collect
```

## Key Concepts Demonstrated

### Stream API
- **Method References**: `User::isActive`
- **Lambda Expressions**: Mapping and sorting logic
- **Functional Interfaces**: Predicate, Function, Comparator
- **Terminal Operation**: `collect()`

### Date Formatting
- **DateTimeFormatter**: Pattern-based formatting of LocalDateTime
- **Format Pattern**: `yyyy-MM-dd HH:mm:ss`

### Exception Handling
- **Custom Exception**: Extends Exception with meaningful messages
- **Defensive Programming**: Null and empty checks

## Requirements

### Java Version
- Java 11+ (uses LocalDateTime, Stream API, var inference)

### Dependencies
- JUnit 5 (Jupiter) 5.9.2 - for testing

## License

MIT License - Free to use and modify

## Author

Created with GitHub Copilot Integration
