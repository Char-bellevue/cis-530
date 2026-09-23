/*
 Natarajan, C. (2026). CIS530-T301 Server-Side Development (2271-1). Bellevue University.
*/
package com.natarajan.week2.enrollment_api.model;

public class Student {
    private Long gId;
    private String gFirstName;
    private String gLastName;
    private String gEmail;
    private String gCourseCode;
    private String gSemester;

    /**
     * Creates an empty student object for JSON deserialization.
     * @return No value; the constructor initializes an empty object.
     */
    public Student() {
    } // end Student

    /**
     * Creates a student with identifying, contact, and enrollment information.
     * @param id Long student identifier.
     * @param firstName String student's first name.
     * @param lastName String student's last name.
     * @param email String student's email address.
     * @param courseCode String course the student is enrolled in.
     * @param semester String semester of enrollment.
     * @return No value; the constructor initializes the student object.
     */
    public Student(Long id, String firstName, String lastName, String email, String courseCode, String semester) {
        this.gId = id;
        this.gFirstName = firstName;
        this.gLastName = lastName;
        this.gEmail = email;
        this.gCourseCode = courseCode;
        this.gSemester = semester;
    } // end Student

    /**
     * Returns the student identifier.
     * @return Long student identifier.
     */
    public Long getId() {
        return gId;
    } // end getId

    /**
     * Updates the student identifier.
     * @param id Long replacement student identifier.
     * @return No value; the field is updated.
     */
    public void setId(Long id) {
        this.gId = id;
    } // end setId

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

    /**
     * Returns the course code the student is enrolled in.
     * @return String course code.
     */
    public String getCourseCode() {
        return gCourseCode;
    } // end getCourseCode

    /**
     * Updates the course code the student is enrolled in.
     * @param courseCode String replacement course code.
     * @return No value; the field is updated.
     */
    public void setCourseCode(String courseCode) {
        this.gCourseCode = courseCode;
    } // end setCourseCode

    /**
     * Returns the semester of enrollment.
     * @return String semester.
     */
    public String getSemester() {
        return gSemester;
    } // end getSemester

    /**
     * Updates the semester of enrollment.
     * @param semester String replacement semester.
     * @return No value; the field is updated.
     */
    public void setSemester(String semester) {
        this.gSemester = semester;
    } // end setSemester

    /**
     * Creates a readable description of this student object.
     * @return String containing all student field values.
     */
    @Override
    public String toString() {
        return "Student{id=" + gId
                + ", firstName='" + gFirstName + "'"
                + ", lastName='" + gLastName + "'"
                + ", email='" + gEmail + "'"
                + ", courseCode='" + gCourseCode + "'"
                + ", semester='" + gSemester + "'}";
    } // end toString
} // end Student
