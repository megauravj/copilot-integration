package com.querybuilder;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the immutable QueryBuilder class.
 */
@DisplayName("QueryBuilder Tests")
public class QueryBuilderTest {

    @Test
    @DisplayName("Should create empty query builder")
    void testCreateQueryBuilder() {
        QueryBuilder builder = QueryBuilder.create();
        assertNotNull(builder);
    }

    @Test
    @DisplayName("Should build SELECT with FROM")
    void testSelectWithFrom() {
        String query = QueryBuilder.create()
                .select("id", "name")
                .from("users")
                .build();
        assertEquals("SELECT id, name FROM users", query);
    }

    @Test
    @DisplayName("Should build SELECT * without specified fields")
    void testSelectAll() {
        String query = QueryBuilder.create()
                .from("users")
                .build();
        assertEquals("SELECT * FROM users", query);
    }

    @Test
    @DisplayName("Should build query with field aliases")
    void testSelectWithAliases() {
        String query = QueryBuilder.create()
                .select("user_id as id", "user_name as name")
                .from("users")
                .build();
        assertEquals("SELECT user_id as id, user_name as name FROM users", query);
    }

    @Test
    @DisplayName("Should throw exception when FROM is not set")
    void testMissingFromClause() {
        assertThrows(IllegalStateException.class, () ->
                QueryBuilder.create()
                        .select("id", "name")
                        .build()
        );
    }

    @Test
    @DisplayName("Should throw exception when trying to set FROM twice")
    void testDuplicateFromClause() {
        assertThrows(IllegalStateException.class, () ->
                QueryBuilder.create()
                        .from("users")
                        .from("products")
        );
    }

    @Test
    @DisplayName("Should build query with WHERE clause")
    void testWhereClause() {
        String query = QueryBuilder.create()
                .from("users")
                .where("age > 18")
                .build();
        assertEquals("SELECT * FROM users WHERE age > 18", query);
    }

    @Test
    @DisplayName("Should build query with WHERE and AND")
    void testWhereAndClause() {
        String query = QueryBuilder.create()
                .from("users")
                .where("age > 18")
                .and("status = 'ACTIVE'")
                .build();
        assertEquals("SELECT * FROM users WHERE age > 18 AND status = 'ACTIVE'", query);
    }

    @Test
    @DisplayName("Should build query with WHERE, AND, and OR")
    void testWhereAndOrClause() {
        String query = QueryBuilder.create()
                .from("users")
                .where("status = 'ACTIVE'")
                .and("age > 18")
                .or("role = 'ADMIN'")
                .build();
        assertEquals("SELECT * FROM users WHERE status = 'ACTIVE' AND age > 18 OR role = 'ADMIN'", query);
    }

    @Test
    @DisplayName("Should throw exception when AND is used before WHERE")
    void testAndBeforeWhere() {
        assertThrows(IllegalStateException.class, () ->
                QueryBuilder.create()
                        .from("users")
                        .and("age > 18")
        );
    }

    @Test
    @DisplayName("Should throw exception when OR is used before WHERE")
    void testOrBeforeWhere() {
        assertThrows(IllegalStateException.class, () ->
                QueryBuilder.create()
                        .from("users")
                        .or("age > 18")
        );
    }

    @Test
    @DisplayName("Should build query with INNER JOIN")
    void testInnerJoin() {
        String query = QueryBuilder.create()
                .from("users u")
                .join(JoinType.INNER, "orders o", "u.id = o.user_id")
                .build();
        assertEquals("SELECT * FROM users u INNER JOIN orders o ON u.id = o.user_id", query);
    }

    @Test
    @DisplayName("Should build query with multiple JOINs")
    void testMultipleJoins() {
        String query = QueryBuilder.create()
                .from("users u")
                .join(JoinType.INNER, "orders o", "u.id = o.user_id")
                .join(JoinType.LEFT, "products p", "o.product_id = p.id")
                .build();
        assertEquals("SELECT * FROM users u INNER JOIN orders o ON u.id = o.user_id LEFT JOIN products p ON o.product_id = p.id", query);
    }

    @Test
    @DisplayName("Should throw exception when JOIN is used before FROM")
    void testJoinBeforeFrom() {
        assertThrows(IllegalStateException.class, () ->
                QueryBuilder.create()
                        .join(JoinType.INNER, "orders", "condition")
        );
    }

    @Test
    @DisplayName("Should build query with ORDER BY")
    void testOrderBy() {
        String query = QueryBuilder.create()
                .from("users")
                .orderBy("name ASC", "created_date DESC")
                .build();
        assertEquals("SELECT * FROM users ORDER BY name ASC, created_date DESC", query);
    }

    @Test
    @DisplayName("Should throw exception when ORDER BY is used before FROM")
    void testOrderByBeforeFrom() {
        assertThrows(IllegalStateException.class, () ->
                QueryBuilder.create()
                        .orderBy("name ASC")
        );
    }

    @Test
    @DisplayName("Should build query with LIMIT")
    void testLimit() {
        String query = QueryBuilder.create()
                .from("users")
                .limit(10)
                .build();
        assertEquals("SELECT * FROM users LIMIT 10", query);
    }

    @Test
    @DisplayName("Should throw exception when LIMIT is less than or equal to 0")
    void testInvalidLimit() {
        assertThrows(IllegalArgumentException.class, () ->
                QueryBuilder.create()
                        .from("users")
                        .limit(0)
        );
    }

    @Test
    @DisplayName("Should build query with LIMIT and OFFSET")
    void testLimitAndOffset() {
        String query = QueryBuilder.create()
                .from("users")
                .limit(10)
                .offset(20)
                .build();
        assertEquals("SELECT * FROM users LIMIT 10 OFFSET 20", query);
    }

    @Test
    @DisplayName("Should throw exception when OFFSET is used before LIMIT")
    void testOffsetBeforeLimit() {
        assertThrows(IllegalStateException.class, () ->
                QueryBuilder.create()
                        .from("users")
                        .offset(10)
        );
    }

    @Test
    @DisplayName("Should throw exception when OFFSET is negative")
    void testNegativeOffset() {
        assertThrows(IllegalArgumentException.class, () ->
                QueryBuilder.create()
                        .from("users")
                        .limit(10)
                        .offset(-5)
        );
    }

    @Test
    @DisplayName("Should build complex query with all clauses")
    void testComplexQuery() {
        String query = QueryBuilder.create()
                .select("u.id", "u.name", "o.order_id")
                .from("users u")
                .join(JoinType.INNER, "orders o", "u.id = o.user_id")
                .where("u.status = 'ACTIVE'")
                .and("o.total > 100")
                .orderBy("o.order_date DESC")
                .limit(50)
                .build();
        assertEquals("SELECT u.id, u.name, o.order_id FROM users u INNER JOIN orders o ON u.id = o.user_id WHERE u.status = 'ACTIVE' AND o.total > 100 ORDER BY o.order_date DESC LIMIT 50", query);
    }

    @Test
    @DisplayName("Should ensure immutability - base query unchanged")
    void testImmutability() {
        QueryBuilder base = QueryBuilder.create()
                .select("id", "name")
                .from("users");
        
        QueryBuilder modified = base.where("age > 18");
        
        String baseQuery = base.build();
        String modifiedQuery = modified.build();
        
        assertEquals("SELECT id, name FROM users", baseQuery);
        assertEquals("SELECT id, name FROM users WHERE age > 18", modifiedQuery);
    }

    @Test
    @DisplayName("Should throw exception for empty select fields")
    void testEmptySelectFields() {
        assertThrows(IllegalArgumentException.class, () ->
                QueryBuilder.create()
                        .select()
        );
    }

    @Test
    @DisplayName("Should throw exception for empty table name in FROM")
    void testEmptyTableName() {
        assertThrows(IllegalArgumentException.class, () ->
                QueryBuilder.create()
                        .from("")
        );
    }

    @Test
    @DisplayName("Should test toString method")
    void testToString() {
        String query = QueryBuilder.create()
                .from("users")
                .where("age > 18")
                .toString();
        assertEquals("SELECT * FROM users WHERE age > 18", query);
    }

    @Test
    @DisplayName("Should test getters return immutable collections")
    void testGettersReturnImmutable() {
        QueryBuilder builder = QueryBuilder.create()
                .select("id", "name")
                .from("users")
                .where("age > 18");
        
        assertThrows(UnsupportedOperationException.class, () ->
                builder.getSelectFields().add("email")
        );
        
        assertThrows(UnsupportedOperationException.class, () ->
                builder.getWhereConditions().add("status = 'ACTIVE'")
        );
    }
}
