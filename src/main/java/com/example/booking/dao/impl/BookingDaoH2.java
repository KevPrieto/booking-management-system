package com.example.booking.dao.impl;

import com.example.booking.dao.BookingDao;
import com.example.booking.db.Db;
import com.example.booking.model.Booking;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookingDaoH2 implements BookingDao {

    private Booking map(ResultSet rs) throws SQLException {
        return new Booking(
                rs.getLong("id"),
                rs.getString("user_name"),
                rs.getString("resource"),
                rs.getTimestamp("start_time").toLocalDateTime(),
                rs.getTimestamp("end_time").toLocalDateTime()
        );
    }

    @Override
    public Booking create(Booking b) {
        String sql = "INSERT INTO bookings(user_name, resource, start_time, end_time) VALUES (?, ?, ?, ?)";
        try (Connection c = Db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, b.getUserName());
            ps.setString(2, b.getResource());
            ps.setTimestamp(3, Timestamp.valueOf(b.getStartTime()));
            ps.setTimestamp(4, Timestamp.valueOf(b.getEndTime()));
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) b.setId(keys.getLong(1));
            }
            return b;
        } catch (SQLException e) {
            throw new RuntimeException("Error creando reserva: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Booking> findAll() {
        String sql = "SELECT * FROM bookings ORDER BY start_time";
        try (Connection c = Db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Booking> list = new ArrayList<>();
            while (rs.next()) list.add(map(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Error listando reservas: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Booking> findById(long id) {
        String sql = "SELECT * FROM bookings WHERE id = ?";
        try (Connection c = Db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(map(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error buscando reserva: " + e.getMessage(), e);
        }
    }

    // Conflicto si: mismo recurso y (startA < endB) AND (endA > startB)
    @Override
    public List<Booking> findConflicts(String resource, LocalDateTime start, LocalDateTime end) {
        String sql = """
            SELECT * FROM bookings
            WHERE resource = ?
              AND start_time < ?
              AND end_time   > ?
            """;
        try (Connection c = Db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, resource);
            ps.setTimestamp(2, Timestamp.valueOf(end));
            ps.setTimestamp(3, Timestamp.valueOf(start));
            try (ResultSet rs = ps.executeQuery()) {
                List<Booking> list = new ArrayList<>();
                while (rs.next()) list.add(map(rs));
                return list;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error comprobando conflictos: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean update(Booking b) {
        String sql = "UPDATE bookings SET user_name=?, resource=?, start_time=?, end_time=? WHERE id=?";
        try (Connection c = Db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, b.getUserName());
            ps.setString(2, b.getResource());
            ps.setTimestamp(3, Timestamp.valueOf(b.getStartTime()));
            ps.setTimestamp(4, Timestamp.valueOf(b.getEndTime()));
            ps.setLong(5, b.getId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando reserva: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean delete(long id) {
        String sql = "DELETE FROM bookings WHERE id=?";
        try (Connection c = Db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error eliminando reserva: " + e.getMessage(), e);
        }
    }
}
