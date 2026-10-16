/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.week6.exception;

/**
 * Exception thrown when a requested post id cannot be found.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Creates the exception with a message.
     * @param message String describing which resource was not found.
     * @return a new ResourceNotFoundException instance.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    } // end ResourceNotFoundException
} // end ResourceNotFoundException
