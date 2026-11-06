/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.studentservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import edu.bellevue.cis530.studentservice.dto.StudentProfileDto;

/**
 * Feign client that calls the Student Profile Service by its Eureka service name,
 * so no host name or port is hard-coded.
 */
@FeignClient(name = "student-profile-service")
public interface StudentProfileClient {

    /**
     * Gets the profile that belongs to one student.
     *
     * @param studentId Long the id of the student whose profile is wanted.
     * @return StudentProfileDto the profile returned by the Student Profile Service.
     */
    @GetMapping("/api/profiles/student/{studentId}")
    StudentProfileDto getProfileByStudentId(@PathVariable("studentId") Long studentId);
} // end StudentProfileClient
