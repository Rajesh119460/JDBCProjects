CREATE DATABASE movie_ticket_booking_db;

USE movie_ticket_booking_db;

CREATE TABLE movies (
    movie_id INT PRIMARY KEY AUTO_INCREMENT,
    movie_name VARCHAR(100) NOT NULL,
    language VARCHAR(50),
    duration INT
);

CREATE TABLE theaters (
    theater_id INT PRIMARY KEY AUTO_INCREMENT,
    theater_name VARCHAR(100) NOT NULL,
    location VARCHAR(100)
);

CREATE TABLE shows (
    show_id INT PRIMARY KEY AUTO_INCREMENT,
    movie_id INT NOT NULL,
    theater_id INT NOT NULL,
    show_time DATETIME NOT NULL,
    total_seats INT NOT NULL,
    available_seats INT NOT NULL,
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id),
    FOREIGN KEY (theater_id) REFERENCES theaters(theater_id)
);

CREATE TABLE bookings (
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    show_id INT NOT NULL,
    customer_name VARCHAR(100) NOT NULL,
    seat_number VARCHAR(20) NOT NULL,
    booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ticket_price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (show_id) REFERENCES shows(show_id),
    UNIQUE (show_id, seat_number)
);

INSERT INTO movies (movie_name, language, duration)
VALUES
('Coolie', 'Telugu', 150),
('War 2', 'Hindi', 170);

INSERT INTO theaters (theater_name, location)
VALUES
('PVR Cinemas', 'Hyderabad'),
('INOX', 'Hyderabad');

INSERT INTO shows
(movie_id, theater_id, show_time, total_seats, available_seats)
VALUES
(1, 1, '2026-10-05 18:30:00', 100, 100),
(2, 2, '2026-10-05 20:00:00', 120, 120);

SELECT * FROM movies;

SELECT * FROM theaters;

SELECT * FROM shows;

SELECT * FROM bookings;


UPDATE movies
SET duration = 160
WHERE movie_name = 'Coolie';


INSERT INTO movies (movie_name, language, duration)
VALUES ('Temporary Movie', 'English', 120);


DELETE FROM movies
WHERE movie_name = 'Temporary Movie';


SELECT * FROM movies;