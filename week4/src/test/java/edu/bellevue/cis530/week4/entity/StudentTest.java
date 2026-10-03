/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.week4.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Test class for the Student entity getters, setters, and no-argument constructor.
 */
class StudentTest {

    /**
     * Checks that a new Student created with the no-argument constructor has empty fields.
     * @return none, the test passes when every field is null.
     */
    @Test
    void newStudentHasNullFields() {
        Student lStudent = new Student();
        assertNull(lStudent.getId());
        assertNull(lStudent.getFirstName());
        assertNull(lStudent.getLastName());
        assertNull(lStudent.getEmail());
        assertNull(lStudent.getMajor());
        assertNull(lStudent.getGpa());
        assertNull(lStudent.getEnrollmentYear());
    } // end newStudentHasNullFields

    /**
     * Checks that every setter stores a value that the matching getter returns.
     * @return none, the test passes when every getter returns the value that was set.
     */
    @Test
    void settersStoreValuesForGetters() {
        Student lStudent = new Student();
        lStudent.setId(1L);
        lStudent.setFirstName("Sara");
        lStudent.setLastName("Feilmann");
        lStudent.setEmail("sara.feilmann@example.com");
        lStudent.setMajor("Computer Science");
        lStudent.setGpa(3.75);
        lStudent.setEnrollmentYear(2024);

        assertEquals(1L, lStudent.getId());
        assertEquals("Sara", lStudent.getFirstName());
        assertEquals("Feilmann", lStudent.getLastName());
        assertEquals("sara.feilmann@example.com", lStudent.getEmail());
        assertEquals("Computer Science", lStudent.getMajor());
        assertEquals(3.75, lStudent.getGpa());
        assertEquals(2024, lStudent.getEnrollmentYear());
    } // end settersStoreValuesForGetters
} // end StudentTest
