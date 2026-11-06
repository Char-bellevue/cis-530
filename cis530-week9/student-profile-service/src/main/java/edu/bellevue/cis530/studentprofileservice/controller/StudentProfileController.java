/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.studentprofileservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.bellevue.cis530.studentprofileservice.entity.StudentProfile;
import edu.bellevue.cis530.studentprofileservice.service.StudentProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * REST controller that exposes the profile endpoints under /api/profiles.
 */
@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class StudentProfileController {

    // The service that holds the business logic for profiles.
    private final StudentProfileService gStudentProfileService;

    /**
     * Creates a profile from a JSON body.
     *
     * @param profile StudentProfile the request body, validated with @Valid.
     * @return ResponseEntity&lt;StudentProfile&gt; a 201 Created response with the saved profile.
     */
    @PostMapping
    public ResponseEntity<StudentProfile> createProfile(@Valid @RequestBody StudentProfile profile) {
        return new ResponseEntity<>(gStudentProfileService.createProfile(profile), HttpStatus.CREATED);
    } // end createProfile

    /**
     * Gets all profiles.
     *
     * @return ResponseEntity&lt;List&lt;StudentProfile&gt;&gt; a 200 OK response with every profile.
     */
    @GetMapping
    public ResponseEntity<List<StudentProfile>> getAllProfiles() {
        return ResponseEntity.ok(gStudentProfileService.getAllProfiles());
    } // end getAllProfiles

    /**
     * Gets one profile by its own id.
     *
     * @param id Long the id of the profile to find.
     * @return ResponseEntity&lt;StudentProfile&gt; a 200 OK response with the matching profile.
     */
    @GetMapping("/{id}")
    public ResponseEntity<StudentProfile> getProfileById(@PathVariable Long id) {
        return ResponseEntity.ok(gStudentProfileService.getProfileById(id));
    } // end getProfileById

    /**
     * Gets the profile that belongs to one student.
     *
     * @param studentId Long the id of the student in the Student Service.
     * @return ResponseEntity&lt;StudentProfile&gt; a 200 OK response with the matching profile.
     */
    @GetMapping("/student/{studentId}")
    public ResponseEntity<StudentProfile> getProfileByStudentId(@PathVariable Long studentId) {
        return ResponseEntity.ok(gStudentProfileService.getProfileByStudentId(studentId));
    } // end getProfileByStudentId

    /**
     * Deletes one profile by its own id.
     *
     * @param id Long the id of the profile to delete.
     * @return ResponseEntity&lt;String&gt; a 200 OK response with a confirmation message.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProfile(@PathVariable Long id) {
        gStudentProfileService.deleteProfile(id);
        return ResponseEntity.ok("Profile deleted successfully");
    } // end deleteProfile
} // end StudentProfileController
