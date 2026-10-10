/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.week5.exception;

/**
 * Exception thrown when a requested student or profile does not exist.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Creates the exception with a message that explains what was not found.
     *
     * @param message String the detail message that describes the missing resource.
     * @return nothing; a new exception object is constructed.
     */
    public ResourceNotFoundException(String message) {
        super(message); // Pass the message to the parent exception.
    } // end ResourceNotFoundException
} // end ResourceNotFoundException
