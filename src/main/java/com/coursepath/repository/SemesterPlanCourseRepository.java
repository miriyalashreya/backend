package com.coursepath.repository;

import com.coursepath.model.SemesterPlanCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SemesterPlanCourseRepository extends JpaRepository<SemesterPlanCourse, Long> {
    List<SemesterPlanCourse> findBySemesterPlanId(Long semesterPlanId);
    void deleteBySemesterPlanId(Long semesterPlanId);
}
