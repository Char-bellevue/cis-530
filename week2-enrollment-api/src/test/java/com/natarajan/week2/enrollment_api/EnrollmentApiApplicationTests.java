package com.natarajan.week2.enrollment_api;

import com.natarajan.week2.enrollment_api.controller.StudentController;
import com.natarajan.week2.enrollment_api.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class EnrollmentApiApplicationTests {

    @Autowired
    private StudentController studentController;

    @Test
    void contextLoads() {
        assertNotNull(studentController);
    }

    @Test
    void shouldReturnStudentsList() {
        List<Student> students = studentController.getStudents();

        assertFalse(students.isEmpty());
        assertEquals("S001", students.get(0).getStudentId());
    }

    @Test
    void shouldCreateStudent() {
        Student student = new Student();
        student.setStudentId("S100");
        student.setFirstName("Alice");
        student.setLastName("Johnson");
        student.setEmail("alice@example.com");

        ResponseEntity<Student> response = studentController.createStudent(student);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("S100", response.getBody().getStudentId());
    }
}
