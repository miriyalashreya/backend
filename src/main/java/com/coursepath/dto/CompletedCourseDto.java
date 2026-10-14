package com.coursepath.dto;

import java.time.LocalDate;

public class CompletedCourseDto {
    private Long id;
    private Long courseId;
    private String courseCode;
    private String courseTitle;
    private Integer credits;
    private Integer semester;
    private Integer semesterCompleted;
    private String grade;
    private LocalDate completionDate;

    public CompletedCourseDto() {
    }

    public CompletedCourseDto(Long id, Long courseId, String courseCode, String courseTitle, Integer credits, Integer semester, Integer semesterCompleted, String grade, LocalDate completionDate) {
        this.id = id;
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.credits = credits;
        this.semester = semester;
        this.semesterCompleted = semesterCompleted;
        this.grade = grade;
        this.completionDate = completionDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public Integer getSemesterCompleted() {
        return semesterCompleted;
    }

    public void setSemesterCompleted(Integer semesterCompleted) {
        this.semesterCompleted = semesterCompleted;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }
}
