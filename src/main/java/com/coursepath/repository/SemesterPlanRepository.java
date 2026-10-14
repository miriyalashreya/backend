package com.coursepath.repository;

import com.coursepath.model.SemesterPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SemesterPlanRepository extends JpaRepository<SemesterPlan, Long> {
    List<SemesterPlan> findByStudentIdOrderByCreatedAtDesc(Long studentId);
}
