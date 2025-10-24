CREATE TABLE IF NOT EXISTS bookings (
  id IDENTITY PRIMARY KEY,                         -- autoincrement
  user_name VARCHAR(100) NOT NULL,
  resource  VARCHAR(100) NOT NULL,
  start_time TIMESTAMP NOT NULL,
  end_time   TIMESTAMP NOT NULL,
  CONSTRAINT chk_time CHECK (end_time > start_time)
);

-- Índices útiles
CREATE INDEX IF NOT EXISTS idx_bookings_resource_time
  ON bookings (resource, start_time, end_time);

-- Opcional: evita solapes exactos duplicados del mismo usuario
CREATE UNIQUE INDEX IF NOT EXISTS uq_exact_duplicate
  ON bookings (user_name, resource, start_time, end_time);
