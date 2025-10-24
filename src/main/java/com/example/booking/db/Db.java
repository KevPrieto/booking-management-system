package com.example.booking.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Provides a singleton-style connection to an H2 database.
 * Uses file-based persistence so data remains after program exit.
 */
public class Db {
    // Persistent local file database under ./data/bookings.mv.db
    private static final String URL = "jdbc:h2:./data/bookings;AUTO_SERVER=TRUE";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    static {
        // Run schema.sql automatically at startup (if table doesn't exist)
        try (Connection c = getConnection();
             Statement st = c.createStatement()) {
            st.execute("""
                CREATE TABLE IF NOT EXISTS bookings (
                    id IDENTITY PRIMARY KEY,
                    user_name VARCHAR(100),
                    resource VARCHAR(100),
                    start_time TIMESTAMP,
                    end_time TIMESTAMP
                );
                """);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database: " + e.getMessage(), e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

