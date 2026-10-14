package com.coursepath.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class SemesterPlanRequest {
    @NotBlank
    private String planName;

    @NotNull
    private Integer targetSemester;

    @NotEmpty
    private List<Long> courseIds;

    private String notes;

    public SemesterPlanRequest() {
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Integer getTargetSemester() {
        return targetSemester;
    }

    public void setTargetSemester(Integer targetSemester) {
        this.targetSemester = targetSemester;
    }

    public List<Long> getCourseIds() {
        return courseIds;
    }

    public void setCourseIds(List<Long> courseIds) {
        this.courseIds = courseIds;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
