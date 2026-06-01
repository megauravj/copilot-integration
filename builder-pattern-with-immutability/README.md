# Immutable QueryBuilder - Builder Pattern with Immutability

## Overview

This project demonstrates the **Builder Pattern** combined with **Immutability** principles in Java. It implements a fluent, immutable `QueryBuilder` class for constructing complex SQL queries in a clean, readable manner.

## Key Features

### 1. **Immutability**
- Each method call returns a new `QueryBuilder` instance
- Original instances remain unchanged, enabling safe concurrent access
- Prevents accidental state mutations

### 2. **Fluent Interface**
- Method chaining for readable, natural query construction
- Chainable operations: `select()` → `from()` → `where()` → `join()` → `orderBy()` → `limit()` → `offset()`

### 3. **State Validation**
- Prevents invalid query construction (e.g., LIMIT before FROM)
- Throws meaningful `IllegalStateException` for invalid operations
- Validates input parameters to ensure non-null and non-empty values

### 4. **Comprehensive Features**

#### SELECT Clause
- Supports field names with optional aliases
- Defaults to `SELECT *` if no fields specified
```java
.select("user_id as id", "user_name as name")
```

#### FROM Clause
- Specifies the main table
- Allows table aliases
```java
.from("users u")
```

#### WHERE Clause
- Basic WHERE conditions
- AND/OR logical operators
```java
.where("age > 18")
.and("status = 'ACTIVE'")
.or("role = 'ADMIN'")
```

#### JOIN Support
- Multiple JOIN types: INNER, LEFT, RIGHT, FULL OUTER, CROSS
- Multiple JOINs in a single query
```java
.join(JoinType.INNER, "orders o", "u.id = o.user_id")
.join(JoinType.LEFT, "products p", "o.product_id = p.id")
```

#### ORDER BY Clause
- Multiple order fields with direction (ASC/DESC)
```java
.orderBy("name ASC", "created_date DESC")
```

#### LIMIT/OFFSET
- Pagination support
- OFFSET requires LIMIT to be set first
```java
.limit(10).offset(20)
```

## Usage Examples

### Example 1: Simple Query
```java
String query = QueryBuilder.create()
    .select("user_id", "user_name", "email")
    .from("users")
    .where("age > 18")
    .build();
// Output: SELECT user_id, user_name, email FROM users WHERE age > 18
```

### Example 2: Query with Aliases and Conditions
```java
String query = QueryBuilder.create()
    .select("user_id as id", "user_name as name", "user_email as email")
    .from("users")
    .where("status = 'ACTIVE'")
    .and("age > 18")
    .build();
// Output: SELECT user_id as id, user_name as name, user_email as email FROM users 
//         WHERE status = 'ACTIVE' AND age > 18
```

### Example 3: Complex Query with Joins
```java
String query = QueryBuilder.create()
    .select("u.user_id", "u.user_name", "o.order_id", "p.product_name")
    .from("users u")
    .join(JoinType.INNER, "orders o", "u.user_id = o.user_id")
    .join(JoinType.LEFT, "products p", "o.product_id = p.product_id")
    .where("u.status = 'ACTIVE'")
    .and("o.order_status = 'COMPLETED'")
    .orderBy("o.order_date DESC")
    .limit(100)
    .build();
```

### Example 4: Pagination Query
```java
String query = QueryBuilder.create()
    .select("user_id", "user_name", "email")
    .from("users")
    .where("is_active = true")
    .orderBy("user_name ASC")
    .limit(20)
    .offset(40)
    .build();
```

### Example 5: Demonstrating Immutability
```java
QueryBuilder baseQuery = QueryBuilder.create()
    .select("id", "name")
    .from("users");

QueryBuilder query1 = baseQuery.where("age > 18");
QueryBuilder query2 = baseQuery.where("is_premium = true");

// All three are different queries:
baseQuery.build();  // SELECT id, name FROM users
query1.build();     // SELECT id, name FROM users WHERE age > 18
query2.build();     // SELECT id, name FROM users WHERE is_premium = true
```

## Project Structure

```
builder-pattern-with-immutability/
├── src/
│   ├── main/java/com/querybuilder/
│   │   ├── JoinType.java              # Enum for JOIN types
│   │   ├── Join.java                  # Class representing a JOIN clause
│   │   ├── QueryBuilder.java          # Main immutable QueryBuilder class
│   │   └── QueryBuilderExample.java   # Comprehensive usage examples
│   └── test/java/com/querybuilder/
│       └── QueryBuilderTest.java      # Unit tests (20+ test cases)
├── pom.xml                             # Maven configuration
└── README.md                           # This file
```

## Design Patterns & Principles

### 1. **Builder Pattern**
- Separates object construction from its representation
- Provides a fluent interface for step-by-step query building
- Enables complex queries through simple, readable code

### 2. **Immutability**
- Each method returns a new instance instead of modifying the current one
- Uses defensive copying for internal collections
- Provides `Collections.unmodifiableList()` in getters
- Thread-safe by design

### 3. **Validation**
- State validation prevents invalid query construction
- Input validation ensures non-null and non-empty values
- Meaningful error messages guide developers

### 4. **Encapsulation**
- Private constructor enforces factory method usage
- Immutable collections prevent external modification
- Clear separation between public API and internal implementation

## Classes and Methods

### JoinType Enum
- **INNER**: `INNER JOIN`
- **LEFT**: `LEFT JOIN`
- **RIGHT**: `RIGHT JOIN`
- **FULL**: `FULL OUTER JOIN`
- **CROSS**: `CROSS JOIN`

### Join Class
- Represents a single JOIN clause
- Immutable data holder
- Implements `toString()` for SQL representation

### QueryBuilder Class

#### Core Methods
- `create()`: Factory method to create a new QueryBuilder
- `select(String... fields)`: Add SELECT fields
- `from(String table)`: Set FROM clause
- `where(String condition)`: Add WHERE condition
- `and(String condition)`: Add AND condition
- `or(String condition)`: Add OR condition
- `join(JoinType, String table, String condition)`: Add JOIN clause
- `orderBy(String... fields)`: Add ORDER BY clause
- `limit(int limit)`: Set LIMIT
- `offset(int offset)`: Set OFFSET
- `build()`: Generate final SQL string

#### Getter Methods (return immutable collections)
- `getSelectFields()`
- `getFromTable()`
- `getWhereConditions()`
- `getJoins()`
- `getOrderByFields()`
- `getLimitValue()`
- `getOffsetValue()`

## Testing

The project includes comprehensive unit tests using JUnit 5:
- 20+ test cases covering all features
- Tests for valid queries
- Tests for invalid states and exceptions
- Tests for immutability guarantees
- Tests for getter immutability

### Run Tests
```bash
mvn test
```

## Building the Project

### Prerequisites
- Java 11 or higher
- Maven 3.6 or higher

### Build
```bash
mvn clean package
```

### Run Examples
```bash
mvn exec:java -Dexec.mainClass="com.querybuilder.QueryBuilderExample"
```

## Error Handling

The QueryBuilder validates all operations and throws appropriate exceptions:

| Operation | Exception | Reason |
|-----------|-----------|--------|
| Missing FROM | `IllegalStateException` | Cannot build query without FROM |
| WHERE before FROM | `IllegalStateException` | Invalid clause order |
| Duplicate FROM | `IllegalStateException` | FROM can only be set once |
| AND/OR before WHERE | `IllegalStateException` | Logical operators require WHERE |
| JOIN before FROM | `IllegalStateException` | JOIN requires FROM |
| OFFSET before LIMIT | `IllegalStateException` | OFFSET requires LIMIT |
| Negative OFFSET | `IllegalArgumentException` | OFFSET must be >= 0 |
| Zero/Negative LIMIT | `IllegalArgumentException` | LIMIT must be > 0 |
| Null/Empty fields | `IllegalArgumentException` | Values cannot be null or empty |

## Performance Considerations

- **Immutability**: Each method creates a new instance, which has slight memory overhead but ensures thread-safety
- **Defensive Copying**: Used internally to prevent external modification
- **Lazy Building**: SQL string is only generated when `build()` is called
- **Unmodifiable Collections**: Getters return unmodifiable views to prevent external changes

## Future Enhancements

- GROUP BY clause support
- HAVING clause support
- Subquery support
- Parameter binding/prepared statements
- Query optimization hints
- Support for UNION/UNION ALL
- Window function support

## License

MIT License - Feel free to use this in your projects!

## Author

Created as a demonstration of the Builder Pattern combined with Immutability principles in Java.
