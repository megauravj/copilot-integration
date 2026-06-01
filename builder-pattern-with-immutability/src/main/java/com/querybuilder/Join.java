package com.querybuilder;

import java.util.Objects;

/**
 * Represents a JOIN clause in a SQL query.
 */
public class Join {
    private final JoinType type;
    private final String table;
    private final String condition;

    public Join(JoinType type, String table, String condition) {
        this.type = Objects.requireNonNull(type, "JoinType cannot be null");
        this.table = Objects.requireNonNull(table, "Table name cannot be null");
        this.condition = Objects.requireNonNull(condition, "Join condition cannot be null");
    }

    public JoinType getType() {
        return type;
    }

    public String getTable() {
        return table;
    }

    public String getCondition() {
        return condition;
    }

    @Override
    public String toString() {
        return type.getSqlKeyword() + " " + table + " ON " + condition;
    }
}
