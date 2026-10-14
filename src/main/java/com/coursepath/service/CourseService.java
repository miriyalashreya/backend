package com.coursepath.service;

import com.coursepath.dto.CourseDto;
import com.coursepath.dto.PrerequisiteDto;
import com.coursepath.model.Course;
import com.coursepath.model.Department;
import com.coursepath.model.Prerequisite;
import com.coursepath.repository.CourseRepository;
import com.coursepath.repository.DepartmentRepository;
import com.coursepath.repository.PrerequisiteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final DepartmentRepository departmentRepository;
    private final PrerequisiteRepository prerequisiteRepository;

    public CourseService(CourseRepository courseRepository, DepartmentRepository departmentRepository, PrerequisiteRepository prerequisiteRepository) {
        this.courseRepository = courseRepository;
        this.departmentRepository = departmentRepository;
        this.prerequisiteRepository = prerequisiteRepository;
    }

    public List<CourseDto> getAllCourses() {
        return courseRepository.findAllByOrderBySemesterAscCodeAsc().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<Course> getCourseEntityById(Long id) {
        return courseRepository.findById(id);
    }

    public Optional<CourseDto> getCourseById(Long id) {
        return courseRepository.findById(id).map(this::convertToDto);
    }

    public List<CourseDto> getCoursesByDepartment(Long departmentId) {
        return courseRepository.findByDepartmentId(departmentId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<CourseDto> getCoursesBySemester(Integer semester) {
        return courseRepository.findBySemester(semester).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public CourseDto createCourse(CourseDto dto) {
        if (courseRepository.existsByCode(dto.getCode())) {
            throw new IllegalArgumentException("Course code " + dto.getCode() + " already exists");
        }

        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + dto.getDepartmentId()));

        Course course = new Course();
        course.setCode(dto.getCode().trim().toUpperCase());
        course.setTitle(dto.getTitle().trim());
        course.setDescription(dto.getDescription());
        course.setCredits(dto.getCredits());
        course.setSemester(dto.getSemester());
        course.setDepartment(department);

        Course saved = courseRepository.save(course);
        return convertToDto(saved);
    }

    @Transactional
    public CourseDto updateCourse(Long id, CourseDto dto) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + id));

        if (!course.getCode().equalsIgnoreCase(dto.getCode()) && courseRepository.existsByCode(dto.getCode())) {
            throw new IllegalArgumentException("Course code " + dto.getCode() + " is already taken by another course");
        }

        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + dto.getDepartmentId()));

        course.setCode(dto.getCode().trim().toUpperCase());
        course.setTitle(dto.getTitle().trim());
        course.setDescription(dto.getDescription());
        course.setCredits(dto.getCredits());
        course.setSemester(dto.getSemester());
        course.setDepartment(department);

        Course saved = courseRepository.save(course);
        return convertToDto(saved);
    }

    @Transactional
    public void deleteCourse(Long id) {
        // Remove relationships where this course is a prerequisite for others
        List<Prerequisite> dependentPrereqs = prerequisiteRepository.findByPrerequisiteCourseId(id);
        prerequisiteRepository.deleteAll(dependentPrereqs);

        courseRepository.deleteById(id);
    }

    public CourseDto convertToDto(Course course) {
        CourseDto dto = new CourseDto();
        dto.setId(course.getId());
        dto.setCode(course.getCode());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        dto.setCredits(course.getCredits());
        dto.setSemester(course.getSemester());

        if (course.getDepartment() != null) {
            dto.setDepartmentId(course.getDepartment().getId());
            dto.setDepartmentCode(course.getDepartment().getCode());
            dto.setDepartmentName(course.getDepartment().getName());
        }

        List<Prerequisite> prereqs = prerequisiteRepository.findByCourseId(course.getId());
        List<PrerequisiteDto> prereqDtos = prereqs.stream().map(p -> {
            PrerequisiteDto pdto = new PrerequisiteDto();
            pdto.setId(p.getId());
            pdto.setCourseId(p.getCourse().getId());
            pdto.setCourseCode(p.getCourse().getCode());
            pdto.setCourseTitle(p.getCourse().getTitle());
            pdto.setPrerequisiteCourseId(p.getPrerequisiteCourse().getId());
            pdto.setPrerequisiteCourseCode(p.getPrerequisiteCourse().getCode());
            pdto.setPrerequisiteCourseTitle(p.getPrerequisiteCourse().getTitle());
            pdto.setPrerequisiteSemester(p.getPrerequisiteCourse().getSemester());
            pdto.setRequirementType(p.getRequirementType());
            pdto.setMinGrade(p.getMinGrade());
            return pdto;
        }).collect(Collectors.toList());

        dto.setPrerequisites(prereqDtos);
        return dto;
    }
}
