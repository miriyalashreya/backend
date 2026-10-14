package com.coursepath.repository;

import com.coursepath.model.StudentCompletedCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentCompletedCourseRepository extends JpaRepository<StudentCompletedCourse, Long> {
    List<StudentCompletedCourse> findByStudentId(Long studentId);
    Optional<StudentCompletedCourse> findByStudentIdAndCourseId(Long studentId, Long courseId);
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
    void deleteByStudentIdAndCourseId(Long studentId, Long courseId);
    void deleteByStudentId(Long studentId);
}
