package com.example.booking;

import com.example.booking.dao.impl.BookingDaoH2;
import com.example.booking.service.BookingService;
import com.example.booking.db.Db;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class BookingServiceTest {

    @BeforeEach
    void resetDatabase() throws Exception {
        try (Connection c = Db.getConnection();
             Statement st = c.createStatement()) {
            // Clean all tables before each test
            st.execute("DROP ALL OBJECTS");
            // Recreate schema
            st.execute("RUNSCRIPT FROM 'classpath:schema.sql'");
        }
    }

    @Test
    void shouldCreateBookingSuccessfully() {
        var service = new BookingService(new BookingDaoH2());
        var start = LocalDateTime.now().plusHours(1);
        var end = start.plusHours(2);

        var booking = service.create("alice", "room-1", start, end);

        assertNotNull(booking.getId(), "Booking ID should not be null");
        assertEquals("alice", booking.getUserName());
        assertEquals("room-1", booking.getResource());
    }

    @Test
    void shouldRejectOverlappingBooking() {
        var service = new BookingService(new BookingDaoH2());
        var start = LocalDateTime.now().plusHours(1);
        var end = start.plusHours(2);

        service.create("alice", "room-1", start, end);

        var overlapStart = start.plusMinutes(30);
        var overlapEnd = end.plusMinutes(30);

        var ex = assertThrows(IllegalStateException.class, () ->
                service.create("bob", "room-1", overlapStart, overlapEnd));

        assertTrue(ex.getMessage().toLowerCase().contains("conflict"));
    }

    @Test
    void shouldListBookings() {
        var service = new BookingService(new BookingDaoH2());
        var start = LocalDateTime.now().plusHours(1);
        var end = start.plusHours(2);

        service.create("charlie", "room-2", start, end);

        var list = service.list();
        assertFalse(list.isEmpty(), "Booking list should not be empty");
    }
}
