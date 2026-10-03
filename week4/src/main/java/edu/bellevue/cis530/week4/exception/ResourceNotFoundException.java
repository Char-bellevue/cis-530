/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.week4.exception;

/**
 * Runtime exception thrown when a requested record does not exist in the database.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Creates the exception with a message that explains what was not found.
     * @param message String the explanation returned to the client
     * @return none, a new exception object is created.
     */
    public ResourceNotFoundException(String message) {
        super(message); // Pass the message to the parent exception class.
    } // end ResourceNotFoundException
} // end ResourceNotFoundException
