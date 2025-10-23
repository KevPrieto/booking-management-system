package com.example.booking.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {
    // Ejecuta schema.sql al iniciar y mantiene la BD viva hasta que el proceso termine.
    private static final String URL =
        "jdbc:h2:mem:bookings;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM 'classpath:schema.sql'";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, "sa", "");
    }
}
