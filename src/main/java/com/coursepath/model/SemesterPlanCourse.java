package com.coursepath.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "semester_plan_courses")
public class SemesterPlanCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "semester_plan_id", nullable = false)
    @JsonIgnoreProperties("plannedCourses")
    private SemesterPlan semesterPlan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(length = 255)
    private String notes;

    public SemesterPlanCourse() {
    }

    public SemesterPlanCourse(SemesterPlan semesterPlan, Course course, String notes) {
        this.semesterPlan = semesterPlan;
        this.course = course;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SemesterPlan getSemesterPlan() {
        return semesterPlan;
    }

    public void setSemesterPlan(SemesterPlan semesterPlan) {
        this.semesterPlan = semesterPlan;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
