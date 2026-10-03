/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.week4.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Global handler that turns exceptions from every controller into clean JSON error responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles requests for a student ID that does not exist.
     * @param exception ResourceNotFoundException the exception that was thrown
     * @return ResponseEntity<Map<String, Object>> a JSON body with HTTP status 404.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException exception) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    } // end handleResourceNotFound

    /**
     * Handles invalid input such as a missing required field, a bad sort direction, or a negative page.
     * @param exception IllegalArgumentException the exception that was thrown
     * @return ResponseEntity<Map<String, Object>> a JSON body with HTTP status 400.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException exception) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    } // end handleIllegalArgument

    /**
     * Handles a path or query value that has the wrong type, such as letters where a number is expected.
     * @param exception MethodArgumentTypeMismatchException the exception that was thrown
     * @return ResponseEntity<Map<String, Object>> a JSON body with HTTP status 400.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleTypeMismatch(MethodArgumentTypeMismatchException exception) {
        String lMessage = "The value for '" + exception.getName() + "' has the wrong type.";
        return buildErrorResponse(HttpStatus.BAD_REQUEST, lMessage);
    } // end handleTypeMismatch

    /**
     * Handles a request body that is missing or is not valid JSON.
     * @param exception HttpMessageNotReadableException the exception that was thrown
     * @return ResponseEntity<Map<String, Object>> a JSON body with HTTP status 400.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleUnreadableBody(HttpMessageNotReadableException exception) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "The request body is missing or is not valid JSON.");
    } // end handleUnreadableBody

    /**
     * Handles database constraint failures such as a null value in a required column.
     * @param exception DataIntegrityViolationException the exception that was thrown
     * @return ResponseEntity<Map<String, Object>> a JSON body with HTTP status 409.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrity(DataIntegrityViolationException exception) {
        return buildErrorResponse(HttpStatus.CONFLICT, "The data conflicts with a database constraint.");
    } // end handleDataIntegrity

    /**
     * Builds the JSON error body that every handler in this class returns.
     * @param status HttpStatus the HTTP status to send back
     * @param message String the explanation shown to the client
     * @return ResponseEntity<Map<String, Object>> the error body together with the HTTP status.
     */
    private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String message) {
        Map<String, Object> lBody = new LinkedHashMap<>(); // Keeps the fields in insertion order.
        lBody.put("timestamp", LocalDateTime.now().toString());
        lBody.put("status", status.value());
        lBody.put("error", status.getReasonPhrase());
        lBody.put("message", message);
        return new ResponseEntity<>(lBody, status);
    } // end buildErrorResponse
} // end GlobalExceptionHandler
