package com.coursepath.controller;

import com.coursepath.dto.CourseDto;
import com.coursepath.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<CourseDto>> getAllCourses(
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) Integer semester) {

        if (departmentId != null) {
            return ResponseEntity.ok(courseService.getCoursesByDepartment(departmentId));
        }
        if (semester != null) {
            return ResponseEntity.ok(courseService.getCoursesBySemester(semester));
        }
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createCourse(@Valid @RequestBody CourseDto courseDto) {
        try {
            CourseDto created = courseService.createCourse(courseDto);
            return ResponseEntity.ok(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Long id, @Valid @RequestBody CourseDto courseDto) {
        try {
            CourseDto updated = courseService.updateCourse(id, courseDto);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        try {
            courseService.deleteCourse(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete course: " + e.getMessage());
        }
    }
}
