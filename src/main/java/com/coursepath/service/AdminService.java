package com.coursepath.service;

import com.coursepath.dto.AdminStatsDto;
import com.coursepath.dto.BottleneckCourseDto;
import com.coursepath.model.Course;
import com.coursepath.model.Prerequisite;
import com.coursepath.model.Role;
import com.coursepath.repository.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final DepartmentRepository departmentRepository;
    private final PrerequisiteRepository prerequisiteRepository;
    private final StudentCompletedCourseRepository completedCourseRepository;
    private final SemesterPlanRepository semesterPlanRepository;

    public AdminService(UserRepository userRepository,
                        CourseRepository courseRepository,
                        DepartmentRepository departmentRepository,
                        PrerequisiteRepository prerequisiteRepository,
                        StudentCompletedCourseRepository completedCourseRepository,
                        SemesterPlanRepository semesterPlanRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.departmentRepository = departmentRepository;
        this.prerequisiteRepository = prerequisiteRepository;
        this.completedCourseRepository = completedCourseRepository;
        this.semesterPlanRepository = semesterPlanRepository;
    }

    public AdminStatsDto getAdminStats() {
        AdminStatsDto stats = new AdminStatsDto();
        stats.setTotalStudents(userRepository.findByRole(Role.STUDENT).size());
        stats.setTotalCourses(courseRepository.count());
        stats.setTotalDepartments(departmentRepository.count());
        stats.setTotalPrerequisites(prerequisiteRepository.count());
        stats.setTotalCompletedEnrollments(completedCourseRepository.count());
        stats.setTotalPlansCreated(semesterPlanRepository.count());

        // Calculate bottleneck courses (courses that serve as prerequisites for the highest number of other courses)
        List<Prerequisite> allPrereqs = prerequisiteRepository.findAll();
        Map<Long, Integer> dependentCounts = new HashMap<>();
        for (Prerequisite p : allPrereqs) {
            Long prereqCourseId = p.getPrerequisiteCourse().getId();
            dependentCounts.put(prereqCourseId, dependentCounts.getOrDefault(prereqCourseId, 0) + 1);
        }

        List<BottleneckCourseDto> bottlenecks = dependentCounts.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .limit(5)
                .map(entry -> {
                    Optional<Course> cOpt = courseRepository.findById(entry.getKey());
                    if (cOpt.isPresent()) {
                        Course c = cOpt.get();
                        return new BottleneckCourseDto(c.getId(), c.getCode(), c.getTitle(), entry.getValue());
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        stats.setTopBottlenecks(bottlenecks);
        return stats;
    }
}
