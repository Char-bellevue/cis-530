/*
 Natarajan, C. (2026). CIS530-T301 Server-Side Development (2271-1). Bellevue University.
*/
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
    private StudentController gStudentController;

    /**
     * Confirms that the Spring application context loads the controller.
     * @return No value; the test fails if the controller is unavailable.
     */
    @Test
    void contextLoads() {
        assertNotNull(gStudentController);
    } // end contextLoads

    /**
     * Confirms that the controller returns the seeded student list.
     * @return No value; the test fails when the list is empty or incorrectly seeded.
     */
    @Test
    void shouldReturnStudentsList() {
        List<Student> lStudents = gStudentController.getStudents();

        assertFalse(lStudents.isEmpty());
        assertEquals("S001", lStudents.get(0).getStudentId());
    } // end shouldReturnStudentsList

    /**
     * Confirms that the controller creates a student with HTTP 201 status.
     * @return No value; the test fails when creation does not return the expected response.
     */
    @Test
    void shouldCreateStudent() {
        Student lStudent = new Student();
        lStudent.setStudentId("S100");
        lStudent.setFirstName("Alice");
        lStudent.setLastName("Johnson");
        lStudent.setEmail("alice@example.com");

        ResponseEntity<Student> lResponse = gStudentController.createStudent(lStudent);

        assertEquals(HttpStatus.CREATED, lResponse.getStatusCode());
        assertEquals("S100", lResponse.getBody().getStudentId());
    } // end shouldCreateStudent
} // end EnrollmentApiApplicationTests
