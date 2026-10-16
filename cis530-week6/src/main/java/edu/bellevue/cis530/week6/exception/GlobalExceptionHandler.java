/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.week6.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Catches exceptions thrown by the controllers and returns clean JSON error responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles a missing post and returns HTTP 404.
     * @param exception ResourceNotFoundException the exception that was thrown.
     * @return ResponseEntity of Map holding the error details with status 404.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException exception) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    } // end handleResourceNotFound

    /**
     * Handles a failed call to the external API and returns HTTP 502.
     * @param exception ApiClientException the exception that was thrown.
     * @return ResponseEntity of Map holding the error details with status 502.
     */
    @ExceptionHandler(ApiClientException.class)
    public ResponseEntity<Map<String, Object>> handleApiClient(ApiClientException exception) {
        return buildErrorResponse(HttpStatus.BAD_GATEWAY, exception.getMessage());
    } // end handleApiClient

    /**
     * Builds the JSON error body shared by every handler in this class.
     * @param status HttpStatus the HTTP status to return.
     * @param message String the message to show the caller.
     * @return ResponseEntity of Map holding the timestamp, status, error, and message.
     */
    private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String message) {
        Map<String, Object> lBody = new LinkedHashMap<>();
        lBody.put("timestamp", LocalDateTime.now().toString());
        lBody.put("status", status.value());
        lBody.put("error", status.getReasonPhrase());
        lBody.put("message", message);
        return new ResponseEntity<>(lBody, status);
    } // end buildErrorResponse
} // end GlobalExceptionHandler
