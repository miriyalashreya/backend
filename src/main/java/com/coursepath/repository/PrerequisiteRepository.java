package com.coursepath.repository;

import com.coursepath.model.Prerequisite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrerequisiteRepository extends JpaRepository<Prerequisite, Long> {
    List<Prerequisite> findByCourseId(Long courseId);
    List<Prerequisite> findByPrerequisiteCourseId(Long prerequisiteCourseId);
    Optional<Prerequisite> findByCourseIdAndPrerequisiteCourseId(Long courseId, Long prerequisiteCourseId);
    boolean existsByCourseIdAndPrerequisiteCourseId(Long courseId, Long prerequisiteCourseId);
    void deleteByCourseIdAndPrerequisiteCourseId(Long courseId, Long prerequisiteCourseId);
}
