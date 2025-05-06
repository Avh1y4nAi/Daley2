package com.ghardalali.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Database Utility class for managing database connections
 */
public class DBUtil {
    // Use only the ghar_dalali_new database
    // XAMPP MySQL connection settings
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/ghar_dalali_new?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = ""; // XAMPP default has no password

    private static boolean initialized = false;

    static {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load MySQL JDBC driver", e);
        }
    }

    /**
     * Initialize the database connection and print table structure
     */
    private static void initialize() {
        if (initialized) {
            return;
        }

        try {
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            System.out.println("Successfully connected to database: ghar_dalali_new");

            // Check if users table exists and print its structure
            printTableStructure(conn, "users");

            conn.close();
            initialized = true;
        } catch (SQLException e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
            System.err.println("Please run the fresh_database_setup.sql script to create the database");
        }
    }

    /**
     * Print the structure of a table
     */
    private static void printTableStructure(Connection conn, String tableName) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("DESCRIBE " + tableName);

            System.out.println("Structure of table '" + tableName + "':");
            while (rs.next()) {
                System.out.println("  " + rs.getString("Field") + " - " + rs.getString("Type"));
            }

            // Count rows
            rs = stmt.executeQuery("SELECT COUNT(*) FROM " + tableName);
            if (rs.next()) {
                System.out.println("  Total rows: " + rs.getInt(1));
            }

            // Show sample data
            rs = stmt.executeQuery("SELECT * FROM " + tableName + " LIMIT 2");
            System.out.println("  Sample data:");
            while (rs.next()) {
                System.out.print("    Row: ");
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    String columnName = rs.getMetaData().getColumnName(i);
                    String value = rs.getString(i);
                    // Don't print password values
                    if (columnName.equalsIgnoreCase("password")) {
                        value = "*****";
                    }
                    System.out.print(columnName + "=" + value + ", ");
                }
                System.out.println();
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Error examining table '" + tableName + "': " + e.getMessage());
        }
    }

    /**
     * Get a database connection
     *
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        // Initialize if not already done
        if (!initialized) {
            initialize();
        }

        try {
            return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
            System.err.println("Connection URL: " + JDBC_URL);
            System.err.println("Connection User: " + JDBC_USER);
            throw e; // Re-throw the exception after logging
        }
    }

    /**
     * Close a database connection safely
     *
     * @param connection Connection to close
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
