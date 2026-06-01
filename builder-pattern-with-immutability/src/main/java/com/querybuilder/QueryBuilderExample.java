package com.querybuilder;

/**
 * Example usage of the immutable QueryBuilder for constructing complex SQL queries.
 */
public class QueryBuilderExample {

    public static void main(String[] args) {
        System.out.println("=== Immutable QueryBuilder Examples ===");
        System.out.println();

        // Example 1: Simple SELECT with WHERE
        System.out.println("Example 1: Simple SELECT with WHERE");
        String query1 = QueryBuilder.create()
                .select("user_id", "user_name", "email")
                .from("users")
                .where("age > 18")
                .build();
        System.out.println(query1);
        System.out.println();

        // Example 2: SELECT with field aliases and WHERE conditions
        System.out.println("Example 2: SELECT with aliases and WHERE conditions");
        String query2 = QueryBuilder.create()
                .select("user_id as id", "user_name as name", "user_email as email")
                .from("users")
                .where("status = 'ACTIVE'")
                .and("age > 18")
                .build();
        System.out.println(query2);
        System.out.println();

        // Example 3: Complex query with JOIN and multiple WHERE conditions
        System.out.println("Example 3: Complex query with INNER JOIN and WHERE conditions");
        String query3 = QueryBuilder.create()
                .select("u.user_id", "u.user_name", "o.order_id", "o.order_date")
                .from("users u")
                .join(JoinType.INNER, "orders o", "u.user_id = o.user_id")
                .where("u.status = 'ACTIVE'")
                .and("o.order_date >= '2024-01-01'")
                .build();
        System.out.println(query3);
        System.out.println();

        // Example 4: Query with multiple JOINs and complex WHERE
        System.out.println("Example 4: Multiple JOINs with complex WHERE logic");
        String query4 = QueryBuilder.create()
                .select("u.user_id", "u.user_name", "o.order_id", "p.product_name", "p.price")
                .from("users u")
                .join(JoinType.INNER, "orders o", "u.user_id = o.user_id")
                .join(JoinType.LEFT, "products p", "o.product_id = p.product_id")
                .where("u.status = 'ACTIVE'")
                .and("o.order_status = 'COMPLETED'")
                .or("o.order_status = 'PENDING'")
                .build();
        System.out.println(query4);
        System.out.println();

        // Example 5: Query with ORDER BY and LIMIT
        System.out.println("Example 5: Query with ORDER BY and LIMIT");
        String query5 = QueryBuilder.create()
                .select("user_id as id", "user_name as name", "created_date")
                .from("users")
                .where("is_premium = true")
                .orderBy("created_date DESC", "user_name ASC")
                .limit(10)
                .build();
        System.out.println(query5);
        System.out.println();

        // Example 6: Query with LIMIT and OFFSET (pagination)
        System.out.println("Example 6: Query with LIMIT and OFFSET (pagination)");
        String query6 = QueryBuilder.create()
                .select("user_id", "user_name", "email")
                .from("users")
                .where("is_active = true")
                .orderBy("user_name ASC")
                .limit(20)
                .offset(40)
                .build();
        System.out.println(query6);
        System.out.println();

        // Example 7: Complex query with all clauses
        System.out.println("Example 7: Complete complex query");
        String query7 = QueryBuilder.create()
                .select("u.user_id as user_id", "u.user_name as user_name", 
                        "COUNT(o.order_id) as total_orders", "SUM(o.total_amount) as total_spent")
                .from("users u")
                .join(JoinType.LEFT, "orders o", "u.user_id = o.user_id")
                .where("u.status = 'ACTIVE'")
                .and("u.country = 'USA'")
                .or("u.country = 'CANADA'")
                .orderBy("total_spent DESC", "user_name ASC")
                .limit(100)
                .build();
        System.out.println(query7);
        System.out.println();

        // Example 8: Demonstrating immutability
        System.out.println("Example 8: Demonstrating immutability");
        QueryBuilder baseQuery = QueryBuilder.create()
                .select("id", "name")
                .from("users");
        
        QueryBuilder query8a = baseQuery.where("age > 18");
        QueryBuilder query8b = baseQuery.where("is_premium = true");
        
        System.out.println("Base Query (no WHERE): " + baseQuery.build());
        System.out.println("Query 8a (WHERE age > 18): " + query8a.build());
        System.out.println("Query 8b (WHERE is_premium): " + query8b.build());
        System.out.println("Base Query remains unchanged: " + baseQuery.build());
        System.out.println();

        // Example 9: Simple SELECT without specifying fields (defaults to *)
        System.out.println("Example 9: SELECT * query");
        String query9 = QueryBuilder.create()
                .from("products")
                .where("price < 100")
                .build();
        System.out.println(query9);
        System.out.println();

        // Example 10: RIGHT JOIN example
        System.out.println("Example 10: RIGHT JOIN example");
        String query10 = QueryBuilder.create()
                .select("d.dept_id", "d.dept_name", "e.emp_id", "e.emp_name")
                .from("departments d")
                .join(JoinType.RIGHT, "employees e", "d.dept_id = e.dept_id")
                .where("d.active = true")
                .orderBy("d.dept_name ASC")
                .build();
        System.out.println(query10);
        System.out.println();
    }
}
