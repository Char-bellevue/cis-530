/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.week4.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Entity class that maps a university student to the student table in MySQL.
 */
@Entity
public class Student {

    // Primary key that MySQL generates automatically.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Required first name of the student.
    @Column(nullable = false)
    private String firstName;

    // Required last name of the student.
    @Column(nullable = false)
    private String lastName;

    // Required email address of the student.
    @Column(nullable = false)
    private String email;

    // Required major of the student.
    @Column(nullable = false)
    private String major;

    // Required grade point average of the student.
    @Column(nullable = false)
    private Double gpa;

    // Required year the student enrolled.
    @Column(nullable = false)
    private Integer enrollmentYear;

    /**
     * No-argument constructor that JPA requires to create Student objects.
     * @return none, a new empty Student object is created.
     */
    public Student() {
        // Intentionally empty because JPA and Jackson fill in the fields.
    } // end Student

    /**
     * Gets the unique identifier of the student.
     * @return Long the unique identifier of the student.
     */
    public Long getId() {
        return id;
    } // end getId

    /**
     * Sets the unique identifier of the student.
     * @param id Long the unique identifier of the student.
     * @return none, the field is updated in place.
     */
    public void setId(Long id) {
        this.id = id; // Store the new value in the field.
    } // end setId

    /**
     * Gets the first name of the student.
     * @return String the first name of the student.
     */
    public String getFirstName() {
        return firstName;
    } // end getFirstName

    /**
     * Sets the first name of the student.
     * @param firstName String the first name of the student.
     * @return none, the field is updated in place.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName; // Store the new value in the field.
    } // end setFirstName

    /**
     * Gets the last name of the student.
     * @return String the last name of the student.
     */
    public String getLastName() {
        return lastName;
    } // end getLastName

    /**
     * Sets the last name of the student.
     * @param lastName String the last name of the student.
     * @return none, the field is updated in place.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName; // Store the new value in the field.
    } // end setLastName

    /**
     * Gets the email address of the student.
     * @return String the email address of the student.
     */
    public String getEmail() {
        return email;
    } // end getEmail

    /**
     * Sets the email address of the student.
     * @param email String the email address of the student.
     * @return none, the field is updated in place.
     */
    public void setEmail(String email) {
        this.email = email; // Store the new value in the field.
    } // end setEmail

    /**
     * Gets the major of the student.
     * @return String the major of the student.
     */
    public String getMajor() {
        return major;
    } // end getMajor

    /**
     * Sets the major of the student.
     * @param major String the major of the student.
     * @return none, the field is updated in place.
     */
    public void setMajor(String major) {
        this.major = major; // Store the new value in the field.
    } // end setMajor

    /**
     * Gets the grade point average of the student.
     * @return Double the grade point average of the student.
     */
    public Double getGpa() {
        return gpa;
    } // end getGpa

    /**
     * Sets the grade point average of the student.
     * @param gpa Double the grade point average of the student.
     * @return none, the field is updated in place.
     */
    public void setGpa(Double gpa) {
        this.gpa = gpa; // Store the new value in the field.
    } // end setGpa

    /**
     * Gets the year the student enrolled.
     * @return Integer the year the student enrolled.
     */
    public Integer getEnrollmentYear() {
        return enrollmentYear;
    } // end getEnrollmentYear

    /**
     * Sets the year the student enrolled.
     * @param enrollmentYear Integer the year the student enrolled.
     * @return none, the field is updated in place.
     */
    public void setEnrollmentYear(Integer enrollmentYear) {
        this.enrollmentYear = enrollmentYear; // Store the new value in the field.
    } // end setEnrollmentYear
} // end Student
