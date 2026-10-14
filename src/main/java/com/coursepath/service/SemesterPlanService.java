package com.coursepath.service;

import com.coursepath.dto.CourseDto;
import com.coursepath.dto.CourseEligibilityDto;
import com.coursepath.dto.SemesterPlanRequest;
import com.coursepath.dto.SemesterPlanResponseDto;
import com.coursepath.model.Course;
import com.coursepath.model.SemesterPlan;
import com.coursepath.model.SemesterPlanCourse;
import com.coursepath.model.User;
import com.coursepath.repository.CourseRepository;
import com.coursepath.repository.SemesterPlanCourseRepository;
import com.coursepath.repository.SemesterPlanRepository;
import com.coursepath.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SemesterPlanService {

    private final SemesterPlanRepository semesterPlanRepository;
    private final SemesterPlanCourseRepository semesterPlanCourseRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final CourseService courseService;
    private final StudentService studentService;

    public SemesterPlanService(SemesterPlanRepository semesterPlanRepository,
                               SemesterPlanCourseRepository semesterPlanCourseRepository,
                               UserRepository userRepository,
                               CourseRepository courseRepository,
                               CourseService courseService,
                               StudentService studentService) {
        this.semesterPlanRepository = semesterPlanRepository;
        this.semesterPlanCourseRepository = semesterPlanCourseRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.courseService = courseService;
        this.studentService = studentService;
    }

    @Transactional
    public SemesterPlanResponseDto createPlan(Long studentId, SemesterPlanRequest request) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found: " + studentId));

        if (request.getCourseIds() == null || request.getCourseIds().isEmpty()) {
            throw new IllegalArgumentException("Please select at least one course for your semester plan.");
        }

        // Verify eligibility of selected courses
        List<CourseEligibilityDto> eligibilities = studentService.checkEligibility(studentId);
        Map<Long, CourseEligibilityDto> eligibilityMap = eligibilities.stream()
                .collect(Collectors.toMap(e -> e.getCourse().getId(), e -> e));

        List<String> ineligibleNames = new ArrayList<>();
        for (Long cId : request.getCourseIds()) {
            CourseEligibilityDto edto = eligibilityMap.get(cId);
            if (edto != null && "LOCKED".equals(edto.getStatus())) {
                ineligibleNames.add(edto.getCourse().getCode());
            }
        }

        if (!ineligibleNames.isEmpty()) {
            throw new IllegalArgumentException("Cannot plan courses with unmet prerequisites: " + String.join(", ", ineligibleNames));
        }

        SemesterPlan plan = new SemesterPlan();
        plan.setStudent(student);
        plan.setPlanName(request.getPlanName());
        plan.setTargetSemester(request.getTargetSemester());
        plan.setStatus("PLANNED");
        plan.setCreatedAt(LocalDateTime.now());
        plan.setUpdatedAt(LocalDateTime.now());

        SemesterPlan savedPlan = semesterPlanRepository.save(plan);

        List<SemesterPlanCourse> planCourses = new ArrayList<>();
        for (Long cId : request.getCourseIds()) {
            Optional<Course> courseOpt = courseRepository.findById(cId);
            if (courseOpt.isPresent()) {
                SemesterPlanCourse spc = new SemesterPlanCourse();
                spc.setSemesterPlan(savedPlan);
                spc.setCourse(courseOpt.get());
                spc.setNotes(request.getNotes());
                planCourses.add(spc);
            }
        }
        semesterPlanCourseRepository.saveAll(planCourses);
        savedPlan.setPlannedCourses(planCourses);

        return convertToDto(savedPlan);
    }

    public List<SemesterPlanResponseDto> getStudentPlans(Long studentId) {
        List<SemesterPlan> plans = semesterPlanRepository.findByStudentIdOrderByCreatedAtDesc(studentId);
        return plans.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Transactional
    public void deletePlan(Long planId) {
        semesterPlanCourseRepository.deleteBySemesterPlanId(planId);
        semesterPlanRepository.deleteById(planId);
    }

    public SemesterPlanResponseDto convertToDto(SemesterPlan plan) {
        SemesterPlanResponseDto dto = new SemesterPlanResponseDto();
        dto.setId(plan.getId());
        dto.setStudentId(plan.getStudent().getId());
        dto.setStudentName(plan.getStudent().getFullName());
        dto.setPlanName(plan.getPlanName());
        dto.setTargetSemester(plan.getTargetSemester());
        dto.setStatus(plan.getStatus());
        dto.setCreatedAt(plan.getCreatedAt());
        dto.setUpdatedAt(plan.getUpdatedAt());

        List<SemesterPlanCourse> spcList = semesterPlanCourseRepository.findBySemesterPlanId(plan.getId());
        List<CourseDto> courseDtos = new ArrayList<>();
        int credits = 0;
        for (SemesterPlanCourse spc : spcList) {
            CourseDto cdto = courseService.convertToDto(spc.getCourse());
            courseDtos.add(cdto);
            credits += spc.getCourse().getCredits();
        }

        dto.setPlannedCourses(courseDtos);
        dto.setTotalPlannedCredits(credits);
        return dto;
    }
}
