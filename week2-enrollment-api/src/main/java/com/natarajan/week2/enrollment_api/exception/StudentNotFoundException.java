/*
 Natarajan, C. (2026). CIS530-T301 Server-Side Development (2271-1). Bellevue University.
*/
package com.natarajan.week2.enrollment_api.exception;

public class StudentNotFoundException extends RuntimeException {

    /**
     * Creates an exception describing which student could not be found.
     * @param message String description of the missing student.
     * @return No value; the constructor initializes the exception.
     */
    public StudentNotFoundException(String message) {
        super(message);
    } // end StudentNotFoundException
} // end StudentNotFoundException
