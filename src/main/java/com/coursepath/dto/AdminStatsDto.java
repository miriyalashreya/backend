package com.coursepath.dto;

import java.util.ArrayList;
import java.util.List;

public class AdminStatsDto {
    private long totalStudents;
    private long totalCourses;
    private long totalDepartments;
    private long totalPrerequisites;
    private long totalCompletedEnrollments;
    private long totalPlansCreated;
    private List<BottleneckCourseDto> topBottlenecks = new ArrayList<>();

    public AdminStatsDto() {
    }

    public long getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(long totalStudents) {
        this.totalStudents = totalStudents;
    }

    public long getTotalCourses() {
        return totalCourses;
    }

    public void setTotalCourses(long totalCourses) {
        this.totalCourses = totalCourses;
    }

    public long getTotalDepartments() {
        return totalDepartments;
    }

    public void setTotalDepartments(long totalDepartments) {
        this.totalDepartments = totalDepartments;
    }

    public long getTotalPrerequisites() {
        return totalPrerequisites;
    }

    public void setTotalPrerequisites(long totalPrerequisites) {
        this.totalPrerequisites = totalPrerequisites;
    }

    public long getTotalCompletedEnrollments() {
        return totalCompletedEnrollments;
    }

    public void setTotalCompletedEnrollments(long totalCompletedEnrollments) {
        this.totalCompletedEnrollments = totalCompletedEnrollments;
    }

    public long getTotalPlansCreated() {
        return totalPlansCreated;
    }

    public void setTotalPlansCreated(long totalPlansCreated) {
        this.totalPlansCreated = totalPlansCreated;
    }

    public List<BottleneckCourseDto> getTopBottlenecks() {
        return topBottlenecks;
    }

    public void setTopBottlenecks(List<BottleneckCourseDto> topBottlenecks) {
        this.topBottlenecks = topBottlenecks;
    }
}
