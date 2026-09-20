/*
 Natarajan, C. (2026). CIS530-T301 Server-Side Development (2271-1). Bellevue University.
*/
package com.natarajan.week2.enrollment_api.model;

public class Student {
    private String gStudentId;
    private String gFirstName;
    private String gLastName;
    private String gEmail;

    /**
     * Creates an empty student object for JSON deserialization.
     * @return No value; the constructor initializes an empty object.
     */
    public Student() {
    } // end Student

    /**
     * Creates a student with identifying and contact information.
     * @param studentId String student identifier.
     * @param firstName String student's first name.
     * @param lastName String student's last name.
     * @param email String student's email address.
     * @return No value; the constructor initializes the student object.
     */
    public Student(String studentId, String firstName, String lastName, String email) {
        this.gStudentId = studentId;
        this.gFirstName = firstName;
        this.gLastName = lastName;
        this.gEmail = email;
    } // end Student

    /**
     * Returns the student identifier.
     * @return String student identifier.
     */
    public String getStudentId() {
        return gStudentId;
    } // end getStudentId

    /**
     * Updates the student identifier.
     * @param studentId String replacement student identifier.
     * @return No value; the field is updated.
     */
    public void setStudentId(String studentId) {
        this.gStudentId = studentId;
    } // end setStudentId

    /**
     * Returns the student's first name.
     * @return String student's first name.
     */
    public String getFirstName() {
        return gFirstName;
    } // end getFirstName

    /**
     * Updates the student's first name.
     * @param firstName String replacement first name.
     * @return No value; the field is updated.
     */
    public void setFirstName(String firstName) {
        this.gFirstName = firstName;
    } // end setFirstName

    /**
     * Returns the student's last name.
     * @return String student's last name.
     */
    public String getLastName() {
        return gLastName;
    } // end getLastName

    /**
     * Updates the student's last name.
     * @param lastName String replacement last name.
     * @return No value; the field is updated.
     */
    public void setLastName(String lastName) {
        this.gLastName = lastName;
    } // end setLastName

    /**
     * Returns the student's email address.
     * @return String student's email address.
     */
    public String getEmail() {
        return gEmail;
    } // end getEmail

    /**
     * Updates the student's email address.
     * @param email String replacement email address.
     * @return No value; the field is updated.
     */
    public void setEmail(String email) {
        this.gEmail = email;
    } // end setEmail
} // end Student
