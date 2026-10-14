package com.coursepath.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "prerequisites", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"course_id", "prerequisite_course_id"})
})
public class Prerequisite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", nullable = false)
    @JsonIgnoreProperties("prerequisites")
    private Course course;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prerequisite_course_id", nullable = false)
    @JsonIgnoreProperties("prerequisites")
    private Course prerequisiteCourse;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RequirementType requirementType = RequirementType.MANDATORY;

    @Column(length = 5)
    private String minGrade = "C";

    public Prerequisite() {
    }

    public Prerequisite(Course course, Course prerequisiteCourse, RequirementType requirementType, String minGrade) {
        this.course = course;
        this.prerequisiteCourse = prerequisiteCourse;
        this.requirementType = requirementType;
        this.minGrade = minGrade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Course getPrerequisiteCourse() {
        return prerequisiteCourse;
    }

    public void setPrerequisiteCourse(Course prerequisiteCourse) {
        this.prerequisiteCourse = prerequisiteCourse;
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
