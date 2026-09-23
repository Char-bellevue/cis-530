/*
 Natarajan, C. (2026). CIS530-T301 Server-Side Development (2271-1). Bellevue University.
*/
package com.natarajan.week2.enrollment_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Builds a centralized JSON error response for any StudentNotFoundException
     * thrown by the service layer.
     * @param exception StudentNotFoundException containing the not-found message.
     * @return ResponseEntity<Object> containing HTTP 404 and a JSON body with
     *         message, status, and timestamp.
     */
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<Object> handleStudentNotFound(StudentNotFoundException exception) {
        Map<String, Object> lErrorBody = new LinkedHashMap<>();
        lErrorBody.put("message", exception.getMessage());
        lErrorBody.put("status", HttpStatus.NOT_FOUND.value());
        lErrorBody.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(lErrorBody);
    } // end handleStudentNotFound
} // end GlobalExceptionHandler
