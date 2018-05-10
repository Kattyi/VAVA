CREATE TABLE conversion
(
  id INTEGER NOT NULL AUTO_INCREMENT,
  timestamp TIMESTAMP,
  file_name VARCHAR(255),
  size INTEGER,
  PRIMARY KEY (id)
);