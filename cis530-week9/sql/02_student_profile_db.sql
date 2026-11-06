CREATE DATABASE IF NOT EXISTS student_profile_db;
USE student_profile_db;

CREATE TABLE IF NOT EXISTS student_profiles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_id BIGINT NOT NULL UNIQUE,
    major VARCHAR(100) NOT NULL,
    gpa DECIMAL(3,2) NOT NULL,
    bio VARCHAR(255)
);

INSERT INTO student_profiles (student_id, major, gpa, bio) VALUES
(1, 'Computer Science', 3.75, 'Enjoys backend development and databases.'),
(2, 'Software Engineering', 3.90, 'Interested in testing and web APIs.'),
(3, 'Information Systems', 3.60, 'Enjoys business and technology integration.');
