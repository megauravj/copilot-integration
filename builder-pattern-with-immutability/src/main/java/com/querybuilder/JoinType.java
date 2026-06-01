package com.querybuilder;

/**
 * Enumeration for SQL JOIN types.
 */
public enum JoinType {
    INNER("INNER JOIN"),
    LEFT("LEFT JOIN"),
    RIGHT("RIGHT JOIN"),
    FULL("FULL OUTER JOIN"),
    CROSS("CROSS JOIN");

    private final String sqlKeyword;

    JoinType(String sqlKeyword) {
        this.sqlKeyword = sqlKeyword;
    }

    public String getSqlKeyword() {
        return sqlKeyword;
    }
}
