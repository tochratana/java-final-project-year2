package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig {

    private static Connection conn;

    // Get connection object for next step
    public static Connection getConn() {
        init();
        return conn;
    }

    // Initialize connection at the first time
    public static void init() {
        if (conn == null) {
            // Step 1: Load Driver of PostgreSQL
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            // Step 2: Define connection URL
            String url = "jdbc:postgresql://localhost:5432/skill_exchange_db";
            Properties info = new Properties();
            info.put("user", "postgres");
            info.put("password", "your_password");

            try {
                conn = DriverManager.getConnection(url, info);
            } catch (SQLException e) {
                System.out.println("SQL error: " + e.getMessage());
                throw new RuntimeException("Database connection failed: " + e);
            }
        }
    }

    // Close connection when application shuts down
    public static void close() {
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}