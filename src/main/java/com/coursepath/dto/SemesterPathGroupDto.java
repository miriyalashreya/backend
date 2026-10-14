package com.coursepath.dto;

import java.util.ArrayList;
import java.util.List;

public class SemesterPathGroupDto {
    private Integer semester;
    private String semesterName;
    private int totalCourses;
    private int completedCourses;
    private int eligibleCourses;
    private int lockedCourses;
    private int totalCredits;
    private List<CourseEligibilityDto> courses = new ArrayList<>();

    public SemesterPathGroupDto() {
    }

    public SemesterPathGroupDto(Integer semester, String semesterName) {
        this.semester = semester;
        this.semesterName = semesterName;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    public int getTotalCourses() {
        return totalCourses;
    }

    public void setTotalCourses(int totalCourses) {
        this.totalCourses = totalCourses;
    }

    public int getCompletedCourses() {
        return completedCourses;
    }

    public void setCompletedCourses(int completedCourses) {
        this.completedCourses = completedCourses;
    }

    public int getEligibleCourses() {
        return eligibleCourses;
    }

    public void setEligibleCourses(int eligibleCourses) {
        this.eligibleCourses = eligibleCourses;
    }

    public int getLockedCourses() {
        return lockedCourses;
    }

    public void setLockedCourses(int lockedCourses) {
        this.lockedCourses = lockedCourses;
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(int totalCredits) {
        this.totalCredits = totalCredits;
    }

    public List<CourseEligibilityDto> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseEligibilityDto> courses) {
        this.courses = courses;
    }
}
