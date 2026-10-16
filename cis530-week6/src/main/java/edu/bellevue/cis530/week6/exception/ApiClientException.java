/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.week6.exception;

/**
 * Exception for general client-side API errors, such as connection or retrieval failures.
 */
public class ApiClientException extends RuntimeException {

    /**
     * Creates the exception with a message and the original cause.
     * @param message String describing what went wrong.
     * @param cause Throwable the original error that triggered this exception.
     * @return a new ApiClientException instance.
     */
    public ApiClientException(String message, Throwable cause) {
        super(message, cause);
    } // end ApiClientException
} // end ApiClientException
