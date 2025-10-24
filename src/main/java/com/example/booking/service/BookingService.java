package com.example.booking.service;

import com.example.booking.dao.BookingDao;
import com.example.booking.model.Booking;

import java.time.LocalDateTime;
import java.util.List;

public class BookingService {
    private final BookingDao dao;

    public BookingService(BookingDao dao) {
        this.dao = dao;
    }

    /**
     * Crea una reserva, validando que:
     * 1. Las fechas sean coherentes (inicio < fin)
     * 2. No exista solape con otra reserva del mismo recurso
     */
    public Booking create(String user, String resource, LocalDateTime start, LocalDateTime end) {
        if (end.isBefore(start) || end.equals(start)) {
            throw new IllegalArgumentException("El fin debe ser posterior al inicio.");
        }

        var conflicts = dao.findConflicts(resource, start, end);
        if (!conflicts.isEmpty()) {
            throw new IllegalStateException("Conflicto: el recurso ya está reservado en ese intervalo.");
        }

        return dao.create(new Booking(user, resource, start, end));
    }

    /** Lista todas las reservas */
    public List<Booking> list() {
        return dao.findAll();
    }

    /** Cancela una reserva por ID */
    public boolean cancel(long id) {
        return dao.delete(id);
    }
}
