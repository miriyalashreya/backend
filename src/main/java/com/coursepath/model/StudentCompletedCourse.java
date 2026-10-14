package com.coursepath.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "student_completed_courses", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"student_id", "course_id"})
})
public class StudentCompletedCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    @JsonIgnoreProperties({"password", "department"})
    private User student;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private Integer semesterCompleted;

    @Column(length = 5)
    private String grade = "A";

    @Column
    private LocalDate completionDate = LocalDate.now();

    public StudentCompletedCourse() {
    }

    public StudentCompletedCourse(User student, Course course, Integer semesterCompleted, String grade) {
        this.student = student;
        this.course = course;
        this.semesterCompleted = semesterCompleted;
        this.grade = grade;
        this.completionDate = LocalDate.now();
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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Integer getSemesterCompleted() {
        return semesterCompleted;
    }

    public void setSemesterCompleted(Integer semesterCompleted) {
        this.semesterCompleted = semesterCompleted;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }
}
