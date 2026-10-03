/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.week4;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Test class that checks the whole application starts. MySQL must be running for this test to pass.
 */
@SpringBootTest
class Week4ApplicationTests {

    /**
     * Checks that the Spring application context loads without an error.
     * @return none, the test passes when no exception is thrown.
     */
    @Test
    void contextLoads() {
        // The test passes when the context starts, so no assertion is needed here.
    } // end contextLoads
} // end Week4ApplicationTests
