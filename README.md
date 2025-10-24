
🧾 Booking Management System

A small console-based Java application to create, list, and cancel bookings. It includes conflict validation (no double bookings for the same resource/time slot), a simple in-memory database (H2), and optional CSV export.

This project was built to practice backend development concepts such as JDBC, DAO pattern, and layered architecture (MVC) using Maven and JUnit 5.

🧠 Overview

The goal of this project is to simulate a real-world booking backend in a simple way:

Users can create, list, or cancel bookings.

The system prevents overlapping reservations.

All data is stored in an H2 in-memory database (initialized automatically on startup).

You can export bookings to CSV for reporting.

🖼️ Screenshot

Here’s how the CLI looks when running locally:

<img width="1106" height="480" alt="image" src="https://github.com/user-attachments/assets/e6bb47ff-9d8a-4c61-8c7c-5830d00f7a6a" />

🏗️ Tech Stack

Java 17

Maven

H2 Database (JDBC)

JUnit 5 for tests

MVC + DAO architecture

Command-line Interface (CLI)

📂 Project Structure

booking-management-system/
├── src/
│ ├── main/java/com/example/booking/
│ │ ├── App.java
│ │ ├── db/DbConnection.java
│ │ ├── model/Booking.java
│ │ ├── dao/BookingDao.java
│ │ ├── dao/BookingDaoH2.java
│ │ ├── service/BookingService.java
│ │ └── util/CsvExporter.java
│ └── test/java/com/example/booking/
│ └── BookingServiceTest.java
├── src/main/resources/schema.sql
├── pom.xml
├── run-booking-app.bat
└── README.md

⚙️ How to Run

Option 1 — From Maven
mvn clean package
mvn exec:java

Option 2 — From the JAR file
After building, go to /target and run:
java -jar booking-management-system-1.0.0-jar-with-dependencies.jar

Or on Windows, just double-click:
run-booking-app.bat

💾 Database

The project uses an H2 in-memory database that initializes automatically on startup.

schema.sql
CREATE TABLE IF NOT EXISTS bookings (
id IDENTITY PRIMARY KEY,
user_name VARCHAR(100) NOT NULL,
resource VARCHAR(100) NOT NULL,
start_time TIMESTAMP NOT NULL,
end_time TIMESTAMP NOT NULL
);
CREATE INDEX idx_resource_time ON bookings(resource, start_time, end_time);

You can change the connection string in DbConnection.java if you want to persist data:
jdbc:h2:./data/bookings

🧪 Testing

Run all unit tests with:
mvn test

Main tests included:

Create booking successfully

Detect overlapping booking conflicts

Cancel and list bookings correctly

🧩 Design Notes

This project follows a simple layered structure:

Layer	Responsibility
Model	Represents data (Booking.java)
DAO	Handles database operations via JDBC
Service	Business logic (conflict validation, rules)
App (CLI)	User interaction (console menu)

Errors are handled gracefully — the app shows clear messages instead of stack traces.

📤 CSV Export

Bookings can be exported to a file named bookings_export.csv in the working directory. Use the Export option in the menu or call CsvExporter.export() manually.

🪪 Example Run

=== Booking Management System ===

Create 2) List 3) Cancel 4) Export CSV 0) Exit

1
User: alice
Resource: room-101
Start (yyyy-MM-dd HH:mm): 2025-10-26 10:00
End (yyyy-MM-dd HH:mm): 2025-10-26 11:00
Created: Booking{id=1, user=alice, res=room-101, 2025-10-26T10:00 -> 2025-10-26T11:00}

🧭 Future Improvements

Add support for multiple resources or booking categories

Build a small Swing or web interface

Store data in a persistent file (H2 file mode or SQLite)

Add email or notification simulation for new bookings

