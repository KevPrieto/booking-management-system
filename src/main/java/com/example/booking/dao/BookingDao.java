package com.example.booking.dao;

import com.example.booking.model.Booking;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingDao {
    Booking create(Booking booking);
    List<Booking> findAll();
    Optional<Booking> findById(long id);
    List<Booking> findConflicts(String resource, LocalDateTime start, LocalDateTime end);
    boolean update(Booking booking);
    boolean delete(long id);
}
