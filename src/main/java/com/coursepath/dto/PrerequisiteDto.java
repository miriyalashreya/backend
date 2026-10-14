package com.coursepath.dto;

import com.coursepath.model.RequirementType;

public class PrerequisiteDto {
    private Long id;
    private Long courseId;
    private String courseCode;
    private String courseTitle;
    private Long prerequisiteCourseId;
    private String prerequisiteCourseCode;
    private String prerequisiteCourseTitle;
    private Integer prerequisiteSemester;
    private RequirementType requirementType;
    private String minGrade;

    public PrerequisiteDto() {
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

    public Long getPrerequisiteCourseId() {
        return prerequisiteCourseId;
    }

    public void setPrerequisiteCourseId(Long prerequisiteCourseId) {
        this.prerequisiteCourseId = prerequisiteCourseId;
    }

    public String getPrerequisiteCourseCode() {
        return prerequisiteCourseCode;
    }

    public void setPrerequisiteCourseCode(String prerequisiteCourseCode) {
        this.prerequisiteCourseCode = prerequisiteCourseCode;
    }

    public String getPrerequisiteCourseTitle() {
        return prerequisiteCourseTitle;
    }

    public void setPrerequisiteCourseTitle(String prerequisiteCourseTitle) {
        this.prerequisiteCourseTitle = prerequisiteCourseTitle;
    }

    public Integer getPrerequisiteSemester() {
        return prerequisiteSemester;
    }

    public void setPrerequisiteSemester(Integer prerequisiteSemester) {
        this.prerequisiteSemester = prerequisiteSemester;
    }

    public RequirementType getRequirementType() {
        return requirementType;
    }

    public void setRequirementType(RequirementType requirementType) {
        this.requirementType = requirementType;
    }

    public String getMinGrade() {
        return minGrade;
    }

    public void setMinGrade(String minGrade) {
        this.minGrade = minGrade;
    }
}
