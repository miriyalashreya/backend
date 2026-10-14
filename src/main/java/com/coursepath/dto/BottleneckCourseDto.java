package com.coursepath.dto;

public class BottleneckCourseDto {
    private Long courseId;
    private String courseCode;
    private String courseTitle;
    private int dependentCoursesCount;

    public BottleneckCourseDto() {
    }

    public BottleneckCourseDto(Long courseId, String courseCode, String courseTitle, int dependentCoursesCount) {
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.dependentCoursesCount = dependentCoursesCount;
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

    public int getDependentCoursesCount() {
        return dependentCoursesCount;
    }

    public void setDependentCoursesCount(int dependentCoursesCount) {
        this.dependentCoursesCount = dependentCoursesCount;
    }
}
