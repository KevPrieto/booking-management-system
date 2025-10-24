package com.example.booking.util;

import com.example.booking.model.Booking;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvExporter {

    public static void export(List<Booking> bookings, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("id,user_name,resource,start_time,end_time\n");
            for (Booking b : bookings) {
                writer.write(String.format("%d,%s,%s,%s,%s\n",
                        b.getId(),
                        b.getUserName(),
                        b.getResource(),
                        b.getStartTime(),
                        b.getEndTime()));
            }
            System.out.println("CSV exported: " + filename);
        } catch (IOException e) {
            System.out.println("Error exporting CSV: " + e.getMessage());
        }
    }
}

