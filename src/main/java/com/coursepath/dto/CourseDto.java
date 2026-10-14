package com.coursepath.dto;

import java.util.ArrayList;
import java.util.List;

public class CourseDto {
    private Long id;
    private String code;
    private String title;
    private String description;
    private Integer credits;
    private Integer semester;
    private Long departmentId;
    private String departmentCode;
    private String departmentName;
    private List<PrerequisiteDto> prerequisites = new ArrayList<>();

    public CourseDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public List<PrerequisiteDto> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(List<PrerequisiteDto> prerequisites) {
        this.prerequisites = prerequisites;
    }
}
