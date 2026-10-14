package com.coursepath.dto;

import java.util.ArrayList;
import java.util.List;

public class CourseEligibilityDto {
    private CourseDto course;
    private String status; // COMPLETED, ELIGIBLE, LOCKED
    private boolean completed;
    private boolean eligible;
    private String gradeEarned;
    private Integer semesterCompleted;
    private List<CourseDto> missingPrerequisites = new ArrayList<>();
    private List<CourseDto> satisfiedPrerequisites = new ArrayList<>();
    private String reason;

    public CourseEligibilityDto() {
    }

    public CourseDto getCourse() {
        return course;
    }

    public void setCourse(CourseDto course) {
        this.course = course;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isEligible() {
        return eligible;
    }

    public void setEligible(boolean eligible) {
        this.eligible = eligible;
    }

    public String getGradeEarned() {
        return gradeEarned;
    }

    public void setGradeEarned(String gradeEarned) {
        this.gradeEarned = gradeEarned;
    }

    public Integer getSemesterCompleted() {
        return semesterCompleted;
    }

    public void setSemesterCompleted(Integer semesterCompleted) {
        this.semesterCompleted = semesterCompleted;
    }

    public List<CourseDto> getMissingPrerequisites() {
        return missingPrerequisites;
    }

    public void setMissingPrerequisites(List<CourseDto> missingPrerequisites) {
        this.missingPrerequisites = missingPrerequisites;
    }

    public List<CourseDto> getSatisfiedPrerequisites() {
        return satisfiedPrerequisites;
    }

    public void setSatisfiedPrerequisites(List<CourseDto> satisfiedPrerequisites) {
        this.satisfiedPrerequisites = satisfiedPrerequisites;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
