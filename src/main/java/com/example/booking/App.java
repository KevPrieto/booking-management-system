package com.example.booking;

import com.example.booking.dao.impl.BookingDaoH2;
import com.example.booking.service.BookingService;
import com.example.booking.util.CsvExporter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class App {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {
        var service = new BookingService(new BookingDaoH2());
        var sc = new Scanner(System.in);

        System.out.println("=== Booking Management System ===");

        while (true) {
            System.out.println("\n1) Create  2) List  3) Cancel  4) Export CSV  0) Exit");
            System.out.print("> ");
            String opt = sc.nextLine().trim();

            try {
                switch (opt) {
                    case "1" -> {
                        System.out.print("User: ");
                        String user = sc.nextLine().trim();
                        System.out.print("Resource: ");
                        String resource = sc.nextLine().trim();
                        System.out.print("Start (yyyy-MM-dd HH:mm): ");
                        LocalDateTime start = LocalDateTime.parse(sc.nextLine().trim(), FMT);
                        System.out.print("End   (yyyy-MM-dd HH:mm): ");
                        LocalDateTime end = LocalDateTime.parse(sc.nextLine().trim(), FMT);

                        var created = service.create(user, resource, start, end);
                        System.out.println("Created: " + created);
                    }

                    case "2" -> {
                        var list = service.list();
                        if (list.isEmpty()) System.out.println("No bookings found.");
                        else list.forEach(System.out::println);
                    }

                    case "3" -> {
                        System.out.print("Booking ID to cancel: ");
                        long id = Long.parseLong(sc.nextLine().trim());
                        boolean ok = service.cancel(id);
                        System.out.println(ok ? "Booking canceled." : "No booking found with that ID.");
                    }

                    case "4" -> {
                        var list = service.list();
                        if (list.isEmpty()) {
                            System.out.println("No bookings to export.");
                        } else {
                            CsvExporter.export(list, "bookings.csv");
                        }
                    }

                    case "0" -> {
                        System.out.println("Goodbye!");
                        return;
                    }

                    default -> System.out.println("Invalid option.");
                }
            } catch (IllegalArgumentException | IllegalStateException ex) {
                // Friendly validation messages (no stacktrace)
                System.out.println("Error: " + ex.getMessage());
            } catch (Exception ex) {
                // Generic fallback
                System.out.println("Unexpected error. Please check your input.");
            }
        }
    }
}
