/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.week4.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;

/**
 * Test class for the GlobalExceptionHandler class.
 */
class GlobalExceptionHandlerTest {

    // The handler that is tested in every method of this class.
    private final GlobalExceptionHandler gHandler = new GlobalExceptionHandler();

    /**
     * Checks that a missing student produces a 404 response with the exception message.
     * @return none, the test passes when the status is 404 and the body has the message.
     */
    @Test
    void notFoundReturns404() {
        ResponseEntity<Map<String, Object>> lResponse =
                gHandler.handleResourceNotFound(new ResourceNotFoundException("Student not found with id: 9"));

        assertEquals(404, lResponse.getStatusCode().value());
        assertEquals("Student not found with id: 9", lResponse.getBody().get("message"));
    } // end notFoundReturns404

    /**
     * Checks that invalid input produces a 400 response with the exception message.
     * @return none, the test passes when the status is 400 and the body has the message.
     */
    @Test
    void illegalArgumentReturns400() {
        ResponseEntity<Map<String, Object>> lResponse =
                gHandler.handleIllegalArgument(new IllegalArgumentException("email is required."));

        assertEquals(400, lResponse.getStatusCode().value());
        assertEquals("email is required.", lResponse.getBody().get("message"));
    } // end illegalArgumentReturns400

    /**
     * Checks that a database constraint failure produces a 409 response.
     * @return none, the test passes when the status is 409.
     */
    @Test
    void dataIntegrityReturns409() {
        ResponseEntity<Map<String, Object>> lResponse =
                gHandler.handleDataIntegrity(new DataIntegrityViolationException("constraint failed"));

        assertEquals(409, lResponse.getStatusCode().value());
    } // end dataIntegrityReturns409

    /**
     * Checks that every error body contains the timestamp, status, error, and message fields.
     * @return none, the test passes when all four fields are present.
     */
    @Test
    void errorBodyContainsAllFields() {
        ResponseEntity<Map<String, Object>> lResponse =
                gHandler.handleResourceNotFound(new ResourceNotFoundException("missing"));
        Map<String, Object> lBody = lResponse.getBody();

        assertNotNull(lBody.get("timestamp"));
        assertEquals(404, lBody.get("status"));
        assertEquals("Not Found", lBody.get("error"));
        assertEquals("missing", lBody.get("message"));
    } // end errorBodyContainsAllFields
} // end GlobalExceptionHandlerTest
