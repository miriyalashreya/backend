package com.coursepath.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SemesterPlanResponseDto {
    private Long id;
    private Long studentId;
    private String studentName;
    private String planName;
    private Integer targetSemester;
    private String status;
    private int totalPlannedCredits;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CourseDto> plannedCourses = new ArrayList<>();

    public SemesterPlanResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalPlannedCredits() {
        return totalPlannedCredits;
    }

    public void setTotalPlannedCredits(int totalPlannedCredits) {
        this.totalPlannedCredits = totalPlannedCredits;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<CourseDto> getPlannedCourses() {
        return plannedCourses;
    }

    public void setPlannedCourses(List<CourseDto> plannedCourses) {
        this.plannedCourses = plannedCourses;
    }
}
