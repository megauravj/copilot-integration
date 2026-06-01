package com.querybuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Immutable QueryBuilder for constructing SQL queries fluently.
 * Each method returns a new QueryBuilder instance, ensuring immutability.
 */
public final class QueryBuilder {
    private final List<String> selectFields;
    private final String fromTable;
    private final List<String> whereConditions;
    private final List<Join> joins;
    private final List<String> orderByFields;
    private final Integer limitValue;
    private final Integer offsetValue;

    /**
     * Private constructor used internally by the builder.
     */
    private QueryBuilder(List<String> selectFields, String fromTable, List<String> whereConditions,
                        List<Join> joins, List<String> orderByFields, Integer limitValue, Integer offsetValue) {
        this.selectFields = selectFields;
        this.fromTable = fromTable;
        this.whereConditions = whereConditions;
        this.joins = joins;
        this.orderByFields = orderByFields;
        this.limitValue = limitValue;
        this.offsetValue = offsetValue;
    }

    /**
     * Creates a new QueryBuilder instance.
     */
    public static QueryBuilder create() {
        return new QueryBuilder(new ArrayList<>(), null, new ArrayList<>(), 
                               new ArrayList<>(), new ArrayList<>(), null, null);
    }

    /**
     * Adds SELECT fields with optional aliases.
     * Example: .select("user_id as id", "user_name as name")
     */
    public QueryBuilder select(String... fields) {
        if (fields == null || fields.length == 0) {
            throw new IllegalArgumentException("SELECT fields cannot be empty");
        }
        List<String> newSelectFields = new ArrayList<>(this.selectFields);
        for (String field : fields) {
            newSelectFields.add(Objects.requireNonNull(field, "Field cannot be null"));
        }
        return new QueryBuilder(newSelectFields, this.fromTable, this.whereConditions,
                               this.joins, this.orderByFields, this.limitValue, this.offsetValue);
    }

    /**
     * Sets the FROM clause.
     * Example: .from("users")
     */
    public QueryBuilder from(String table) {
        if (this.fromTable != null) {
            throw new IllegalStateException("FROM clause already set");
        }
        Objects.requireNonNull(table, "Table name cannot be null");
        if (table.trim().isEmpty()) {
            throw new IllegalArgumentException("Table name cannot be empty");
        }
        return new QueryBuilder(this.selectFields, table, this.whereConditions,
                               this.joins, this.orderByFields, this.limitValue, this.offsetValue);
    }

    /**
     * Adds a WHERE condition.
     * Example: .where("age > 18")
     */
    public QueryBuilder where(String condition) {
        validateFromClauseExists();
        Objects.requireNonNull(condition, "Condition cannot be null");
        if (condition.trim().isEmpty()) {
            throw new IllegalArgumentException("Condition cannot be empty");
        }
        List<String> newWhereConditions = new ArrayList<>(this.whereConditions);
        newWhereConditions.add(condition);
        return new QueryBuilder(this.selectFields, this.fromTable, newWhereConditions,
                               this.joins, this.orderByFields, this.limitValue, this.offsetValue);
    }

    /**
     * Adds an AND condition to the WHERE clause.
     * Example: .where("age > 18").and("status = 'ACTIVE'")
     */
    public QueryBuilder and(String condition) {
        if (this.whereConditions.isEmpty()) {
            throw new IllegalStateException("Cannot use AND before WHERE");
        }
        Objects.requireNonNull(condition, "Condition cannot be null");
        if (condition.trim().isEmpty()) {
            throw new IllegalArgumentException("Condition cannot be empty");
        }
        List<String> newWhereConditions = new ArrayList<>(this.whereConditions);
        newWhereConditions.add("AND " + condition);
        return new QueryBuilder(this.selectFields, this.fromTable, newWhereConditions,
                               this.joins, this.orderByFields, this.limitValue, this.offsetValue);
    }

    /**
     * Adds an OR condition to the WHERE clause.
     * Example: .where("age > 18").and("status = 'ACTIVE'").or("role = 'ADMIN'")
     */
    public QueryBuilder or(String condition) {
        if (this.whereConditions.isEmpty()) {
            throw new IllegalStateException("Cannot use OR before WHERE");
        }
        Objects.requireNonNull(condition, "Condition cannot be null");
        if (condition.trim().isEmpty()) {
            throw new IllegalArgumentException("Condition cannot be empty");
        }
        List<String> newWhereConditions = new ArrayList<>(this.whereConditions);
        newWhereConditions.add("OR " + condition);
        return new QueryBuilder(this.selectFields, this.fromTable, newWhereConditions,
                               this.joins, this.orderByFields, this.limitValue, this.offsetValue);
    }

    /**
     * Adds a JOIN clause.
     * Example: .join(JoinType.INNER, "orders", "users.id = orders.user_id")
     */
    public QueryBuilder join(JoinType type, String table, String condition) {
        validateFromClauseExists();
        Objects.requireNonNull(type, "JoinType cannot be null");
        Objects.requireNonNull(table, "Table name cannot be null");
        Objects.requireNonNull(condition, "Join condition cannot be null");
        
        if (table.trim().isEmpty()) {
            throw new IllegalArgumentException("Table name cannot be empty");
        }
        if (condition.trim().isEmpty()) {
            throw new IllegalArgumentException("Join condition cannot be empty");
        }
        
        List<Join> newJoins = new ArrayList<>(this.joins);
        newJoins.add(new Join(type, table, condition));
        return new QueryBuilder(this.selectFields, this.fromTable, this.whereConditions,
                               newJoins, this.orderByFields, this.limitValue, this.offsetValue);
    }

    /**
     * Adds ORDER BY clause.
     * Example: .orderBy("name ASC", "created_date DESC")
     */
    public QueryBuilder orderBy(String... fields) {
        validateFromClauseExists();
        if (fields == null || fields.length == 0) {
            throw new IllegalArgumentException("ORDER BY fields cannot be empty");
        }
        List<String> newOrderByFields = new ArrayList<>(this.orderByFields);
        for (String field : fields) {
            Objects.requireNonNull(field, "Field cannot be null");
            if (field.trim().isEmpty()) {
                throw new IllegalArgumentException("Field cannot be empty");
            }
            newOrderByFields.add(field);
        }
        return new QueryBuilder(this.selectFields, this.fromTable, this.whereConditions,
                               this.joins, newOrderByFields, this.limitValue, this.offsetValue);
    }

    /**
     * Sets the LIMIT clause.
     * Example: .limit(10)
     */
    public QueryBuilder limit(int limit) {
        validateFromClauseExists();
        if (limit <= 0) {
            throw new IllegalArgumentException("LIMIT must be greater than 0");
        }
        return new QueryBuilder(this.selectFields, this.fromTable, this.whereConditions,
                               this.joins, this.orderByFields, limit, this.offsetValue);
    }

    /**
     * Sets the OFFSET clause.
     * Example: .offset(20)
     */
    public QueryBuilder offset(int offset) {
        validateFromClauseExists();
        if (this.limitValue == null) {
            throw new IllegalStateException("OFFSET requires LIMIT to be set first");
        }
        if (offset < 0) {
            throw new IllegalArgumentException("OFFSET cannot be negative");
        }
        return new QueryBuilder(this.selectFields, this.fromTable, this.whereConditions,
                               this.joins, this.orderByFields, this.limitValue, offset);
    }

    /**
     * Builds and returns the final SQL query string.
     */
    public String build() {
        validateQuery();
        StringBuilder sql = new StringBuilder();

        // SELECT clause
        sql.append("SELECT ");
        if (selectFields.isEmpty()) {
            sql.append("*");
        } else {
            sql.append(String.join(", ", selectFields));
        }

        // FROM clause
        sql.append(" FROM ").append(fromTable);

        // JOIN clauses
        for (Join join : joins) {
            sql.append(" ").append(join.toString());
        }

        // WHERE clause
        if (!whereConditions.isEmpty()) {
            sql.append(" WHERE ");
            sql.append(String.join(" ", whereConditions));
        }

        // ORDER BY clause
        if (!orderByFields.isEmpty()) {
            sql.append(" ORDER BY ").append(String.join(", ", orderByFields));
        }

        // LIMIT clause
        if (limitValue != null) {
            sql.append(" LIMIT ").append(limitValue);
        }

        // OFFSET clause
        if (offsetValue != null) {
            sql.append(" OFFSET ").append(offsetValue);
        }

        return sql.toString();
    }

    /**
     * Returns the generated SQL query string.
     */
    @Override
    public String toString() {
        try {
            return build();
        } catch (IllegalStateException e) {
            return "[Incomplete Query: " + e.getMessage() + "]";        }
    }

    /**
     * Validates that the FROM clause has been set.
     */
    private void validateFromClauseExists() {
        if (this.fromTable == null) {
            throw new IllegalStateException("FROM clause must be set before adding WHERE, JOIN, ORDER BY, LIMIT, or OFFSET");
        }
    }

    /**
     * Validates that the query is in a valid state for building.
     */
    private void validateQuery() {
        if (this.fromTable == null) {
            throw new IllegalStateException("FROM clause is required to build a query");
        }
    }

    /**
     * Returns an immutable view of the SELECT fields.
     */
    public List<String> getSelectFields() {
        return Collections.unmodifiableList(selectFields);
    }

    /**
     * Returns the FROM table.
     */
    public String getFromTable() {
        return fromTable;
    }

    /**
     * Returns an immutable view of the WHERE conditions.
     */
    public List<String> getWhereConditions() {
        return Collections.unmodifiableList(whereConditions);
    }

    /**
     * Returns an immutable view of the JOINs.
     */
    public List<Join> getJoins() {
        return Collections.unmodifiableList(joins);
    }

    /**
     * Returns an immutable view of the ORDER BY fields.
     */
    public List<String> getOrderByFields() {
        return Collections.unmodifiableList(orderByFields);
    }

    /**
     * Returns the LIMIT value.
     */
    public Integer getLimitValue() {
        return limitValue;
    }

    /**
     * Returns the OFFSET value.
     */
    public Integer getOffsetValue() {
        return offsetValue;
    }
}
