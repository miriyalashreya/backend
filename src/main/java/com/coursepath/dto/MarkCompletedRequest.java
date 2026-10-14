package com.coursepath.dto;

import jakarta.validation.constraints.NotNull;

public class MarkCompletedRequest {
    @NotNull
    private Long courseId;
    private Integer semesterCompleted = 1;
    private String grade = "A";

    public MarkCompletedRequest() {
    }

    public MarkCompletedRequest(Long courseId, Integer semesterCompleted, String grade) {
        this.courseId = courseId;
        this.semesterCompleted = semesterCompleted;
        this.grade = grade;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
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
}
