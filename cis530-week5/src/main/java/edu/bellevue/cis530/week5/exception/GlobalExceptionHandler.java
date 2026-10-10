/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.week5.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Converts exceptions thrown by the controllers and services into clear JSON error responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Builds the common JSON body that every error response starts with.
     *
     * @param status HttpStatus the HTTP status that the response will use.
     * @param message String the human-readable message that explains the error.
     * @return Map&lt;String, Object&gt; the body containing the timestamp, status, error, and message.
     */
    private Map<String, Object> buildBody(HttpStatus status, String message) {
        Map<String, Object> lBody = new LinkedHashMap<>(); // Keep the keys in a predictable order.
        lBody.put("timestamp", LocalDateTime.now().toString());
        lBody.put("status", status.value());
        lBody.put("error", status.getReasonPhrase());
        lBody.put("message", message);
        return lBody;
    } // end buildBody

    /**
     * Handles failed Bean Validation on a request body and returns every field name with its messages.
     *
     * @param exception MethodArgumentNotValidException the exception raised when @Valid fails.
     * @return ResponseEntity&lt;Map&lt;String, Object&gt;&gt; a 400 Bad Request response with a fieldErrors map.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException exception) {
        Map<String, List<String>> lFieldErrors = new LinkedHashMap<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            List<String> lMessages = lFieldErrors.get(fieldError.getField());
            if (lMessages == null) {
                lMessages = new ArrayList<>(); // Create the list the first time this field fails.
                lFieldErrors.put(fieldError.getField(), lMessages);
            }
            lMessages.add(fieldError.getDefaultMessage());
        }
        Map<String, Object> lBody = buildBody(HttpStatus.BAD_REQUEST, "Validation failed for one or more fields.");
        lBody.put("fieldErrors", lFieldErrors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(lBody);
    } // end handleValidationException

    /**
     * Handles a request body that is missing or is not valid JSON for the expected type.
     *
     * @param exception HttpMessageNotReadableException the exception raised when the body cannot be read.
     * @return ResponseEntity&lt;Map&lt;String, Object&gt;&gt; a 400 Bad Request response with a short message.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleUnreadableBody(HttpMessageNotReadableException exception) {
        Map<String, Object> lBody = buildBody(HttpStatus.BAD_REQUEST,
                "The request body is missing or contains malformed JSON or a value of the wrong type.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(lBody);
    } // end handleUnreadableBody

    /**
     * Handles a request for a student or profile that does not exist.
     *
     * @param exception ResourceNotFoundException the exception raised by the service layer.
     * @return ResponseEntity&lt;Map&lt;String, Object&gt;&gt; a 404 Not Found response with the exception message.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException exception) {
        Map<String, Object> lBody = buildBody(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(lBody);
    } // end handleResourceNotFound

    /**
     * Handles a database constraint violation, such as saving a duplicate email address.
     *
     * @param exception DataIntegrityViolationException the exception raised by the database layer.
     * @return ResponseEntity&lt;Map&lt;String, Object&gt;&gt; a 409 Conflict response with a short message.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolation(DataIntegrityViolationException exception) {
        Map<String, Object> lBody = buildBody(HttpStatus.CONFLICT,
                "The data conflicts with an existing record. The email address may already be in use.");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(lBody);
    } // end handleDataIntegrityViolation
} // end GlobalExceptionHandler
