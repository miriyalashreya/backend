package com.coursepath.service;

import com.coursepath.dto.PrerequisiteDto;
import com.coursepath.model.Course;
import com.coursepath.model.Prerequisite;
import com.coursepath.model.RequirementType;
import com.coursepath.repository.CourseRepository;
import com.coursepath.repository.PrerequisiteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PrerequisiteService {

    private final PrerequisiteRepository prerequisiteRepository;
    private final CourseRepository courseRepository;

    public PrerequisiteService(PrerequisiteRepository prerequisiteRepository, CourseRepository courseRepository) {
        this.prerequisiteRepository = prerequisiteRepository;
        this.courseRepository = courseRepository;
    }

    public List<PrerequisiteDto> getPrerequisitesForCourse(Long courseId) {
        List<Prerequisite> list = prerequisiteRepository.findByCourseId(courseId);
        List<PrerequisiteDto> result = new ArrayList<>();
        for (Prerequisite p : list) {
            PrerequisiteDto dto = new PrerequisiteDto();
            dto.setId(p.getId());
            dto.setCourseId(p.getCourse().getId());
            dto.setCourseCode(p.getCourse().getCode());
            dto.setCourseTitle(p.getCourse().getTitle());
            dto.setPrerequisiteCourseId(p.getPrerequisiteCourse().getId());
            dto.setPrerequisiteCourseCode(p.getPrerequisiteCourse().getCode());
            dto.setPrerequisiteCourseTitle(p.getPrerequisiteCourse().getTitle());
            dto.setPrerequisiteSemester(p.getPrerequisiteCourse().getSemester());
            dto.setRequirementType(p.getRequirementType());
            dto.setMinGrade(p.getMinGrade());
            result.add(dto);
        }
        return result;
    }

    @Transactional
    public PrerequisiteDto addPrerequisite(Long courseId, Long prerequisiteCourseId, RequirementType requirementType, String minGrade) {
        if (courseId.equals(prerequisiteCourseId)) {
            throw new IllegalArgumentException("A course cannot have itself as a prerequisite!");
        }

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Target course not found: " + courseId));

        Course prerequisiteCourse = courseRepository.findById(prerequisiteCourseId)
                .orElseThrow(() -> new IllegalArgumentException("Prerequisite course not found: " + prerequisiteCourseId));

        if (prerequisiteRepository.existsByCourseIdAndPrerequisiteCourseId(courseId, prerequisiteCourseId)) {
            throw new IllegalArgumentException("Prerequisite relationship already exists!");
        }

        // Cycle Detection: Check if prerequisiteCourse transitively requires courseId
        if (causesCycle(courseId, prerequisiteCourseId)) {
            throw new IllegalArgumentException("Cannot add prerequisite: this would cause a circular dependency loop between "
                    + course.getCode() + " and " + prerequisiteCourse.getCode() + "!");
        }

        Prerequisite prerequisite = new Prerequisite();
        prerequisite.setCourse(course);
        prerequisite.setPrerequisiteCourse(prerequisiteCourse);
        prerequisite.setRequirementType(requirementType != null ? requirementType : RequirementType.MANDATORY);
        prerequisite.setMinGrade(minGrade != null && !minGrade.isBlank() ? minGrade.trim() : "C");

        Prerequisite saved = prerequisiteRepository.save(prerequisite);

        PrerequisiteDto dto = new PrerequisiteDto();
        dto.setId(saved.getId());
        dto.setCourseId(course.getId());
        dto.setCourseCode(course.getCode());
        dto.setCourseTitle(course.getTitle());
        dto.setPrerequisiteCourseId(prerequisiteCourse.getId());
        dto.setPrerequisiteCourseCode(prerequisiteCourse.getCode());
        dto.setPrerequisiteCourseTitle(prerequisiteCourse.getTitle());
        dto.setPrerequisiteSemester(prerequisiteCourse.getSemester());
        dto.setRequirementType(saved.getRequirementType());
        dto.setMinGrade(saved.getMinGrade());
        return dto;
    }

    @Transactional
    public void removePrerequisite(Long prerequisiteId) {
        prerequisiteRepository.deleteById(prerequisiteId);
    }

    /**
     * Cycle detection check:
     * We want to add an edge: courseId -> prerequisiteCourseId (courseId requires prerequisiteCourseId).
     * If there is already a path from prerequisiteCourseId to courseId, adding this edge will close a cycle.
     */
    private boolean causesCycle(Long courseId, Long prerequisiteCourseId) {
        Set<Long> visited = new HashSet<>();
        Queue<Long> queue = new LinkedList<>();

        // Start from prerequisiteCourseId and traverse down its prerequisites
        queue.add(prerequisiteCourseId);
        visited.add(prerequisiteCourseId);

        while (!queue.isEmpty()) {
            Long current = queue.poll();
            if (current.equals(courseId)) {
                return true; // Found a path back to courseId! Cycle detected.
            }

            List<Prerequisite> prereqsOfCurrent = prerequisiteRepository.findByCourseId(current);
            for (Prerequisite p : prereqsOfCurrent) {
                Long nextId = p.getPrerequisiteCourse().getId();
                if (!visited.contains(nextId)) {
                    visited.add(nextId);
                    queue.add(nextId);
                }
            }
        }

        return false;
    }
}
