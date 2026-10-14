package com.coursepath.dto;

import java.util.ArrayList;
import java.util.List;

public class PathSummaryDto {
    private int totalDegreeCourses;
    private int completedCoursesCount;
    private int eligibleCoursesCount;
    private int lockedCoursesCount;
    private int totalDegreeCredits;
    private int completedCredits;
    private double progressPercentage;
    private double cumulativeGpa;
    private List<CourseEligibilityDto> eligibleNextCourses = new ArrayList<>();
    private List<SemesterPathGroupDto> semesterRoadmap = new ArrayList<>();
    private List<CourseEligibilityDto> allCourses = new ArrayList<>();

    public PathSummaryDto() {
    }

    public int getTotalDegreeCourses() {
        return totalDegreeCourses;
    }

    public void setTotalDegreeCourses(int totalDegreeCourses) {
        this.totalDegreeCourses = totalDegreeCourses;
    }

    public int getCompletedCoursesCount() {
        return completedCoursesCount;
    }

    public void setCompletedCoursesCount(int completedCoursesCount) {
        this.completedCoursesCount = completedCoursesCount;
    }

    public int getEligibleCoursesCount() {
        return eligibleCoursesCount;
    }

    public void setEligibleCoursesCount(int eligibleCoursesCount) {
        this.eligibleCoursesCount = eligibleCoursesCount;
    }

    public int getLockedCoursesCount() {
        return lockedCoursesCount;
    }

    public void setLockedCoursesCount(int lockedCoursesCount) {
        this.lockedCoursesCount = lockedCoursesCount;
    }

    public int getTotalDegreeCredits() {
        return totalDegreeCredits;
    }

    public void setTotalDegreeCredits(int totalDegreeCredits) {
        this.totalDegreeCredits = totalDegreeCredits;
    }

    public int getCompletedCredits() {
        return completedCredits;
    }

    public void setCompletedCredits(int completedCredits) {
        this.completedCredits = completedCredits;
    }

    public double getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(double progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public double getCumulativeGpa() {
        return cumulativeGpa;
    }

    public void setCumulativeGpa(double cumulativeGpa) {
        this.cumulativeGpa = cumulativeGpa;
    }

    public List<CourseEligibilityDto> getEligibleNextCourses() {
        return eligibleNextCourses;
    }

    public void setEligibleNextCourses(List<CourseEligibilityDto> eligibleNextCourses) {
        this.eligibleNextCourses = eligibleNextCourses;
    }

    public List<SemesterPathGroupDto> getSemesterRoadmap() {
        return semesterRoadmap;
    }

    public void setSemesterRoadmap(List<SemesterPathGroupDto> semesterRoadmap) {
        this.semesterRoadmap = semesterRoadmap;
    }

    public List<CourseEligibilityDto> getAllCourses() {
        return allCourses;
    }

    public void setAllCourses(List<CourseEligibilityDto> allCourses) {
        this.allCourses = allCourses;
    }
}
