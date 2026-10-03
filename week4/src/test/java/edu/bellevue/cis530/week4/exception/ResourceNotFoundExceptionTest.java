/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.week4.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.Test;

/**
 * Test class for the ResourceNotFoundException class.
 */
class ResourceNotFoundExceptionTest {

    /**
     * Checks that the exception keeps the message it was created with.
     * @return none, the test passes when getMessage returns the original text.
     */
    @Test
    void exceptionKeepsMessage() {
        ResourceNotFoundException lException = new ResourceNotFoundException("Student not found with id: 7");
        assertEquals("Student not found with id: 7", lException.getMessage());
    } // end exceptionKeepsMessage

    /**
     * Checks that the exception is an unchecked RuntimeException.
     * @return none, the test passes when the exception extends RuntimeException.
     */
    @Test
    void exceptionIsRuntimeException() {
        ResourceNotFoundException lException = new ResourceNotFoundException("missing");
        assertInstanceOf(RuntimeException.class, lException);
    } // end exceptionIsRuntimeException
} // end ResourceNotFoundExceptionTest
