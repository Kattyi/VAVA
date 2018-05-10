CREATE TABLE conversion
(
  id integer not null,
  timestamp TIMESTAMP,
  file_name VARCHAR(255),
  size INTEGER,
  primary key(id)
);