package com.coursepath.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "semester_plans")
public class SemesterPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    @JsonIgnoreProperties({"password", "department"})
    private User student;

    @Column(nullable = false, length = 100)
    private String planName;

    @Column(nullable = false)
    private Integer targetSemester;

    @Column(length = 20)
    private String status = "PLANNED"; // DRAFT, PLANNED, ENROLLED

    @Column
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column
    private LocalDateTime updatedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "semesterPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("semesterPlan")
    private List<SemesterPlanCourse> plannedCourses = new ArrayList<>();

    public SemesterPlan() {
    }

    public SemesterPlan(User student, String planName, Integer targetSemester) {
        this.student = student;
        this.planName = planName;
        this.targetSemester = targetSemester;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
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

    public List<SemesterPlanCourse> getPlannedCourses() {
        return plannedCourses;
    }

    public void setPlannedCourses(List<SemesterPlanCourse> plannedCourses) {
        this.plannedCourses = plannedCourses;
    }
}
