package com.coursepath.controller;

import com.coursepath.dto.*;
import com.coursepath.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{studentId}/completed")
    public ResponseEntity<List<CompletedCourseDto>> getCompletedCourses(@PathVariable Long studentId) {
        return ResponseEntity.ok(studentService.getCompletedCourses(studentId));
    }

    @PostMapping("/{studentId}/completed")
    public ResponseEntity<?> markCourseCompleted(@PathVariable Long studentId, @RequestBody MarkCompletedRequest request) {
        try {
            CompletedCourseDto dto = studentService.markCourseCompleted(
                    studentId,
                    request.getCourseId(),
                    request.getSemesterCompleted(),
                    request.getGrade()
            );
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{studentId}/completed/{courseId}")
    public ResponseEntity<?> removeCompletedCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        try {
            studentService.removeCompletedCourse(studentId, courseId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{studentId}/completed/bulk")
    public ResponseEntity<?> setCompletedCoursesBulk(@PathVariable Long studentId, @RequestBody List<Long> courseIds) {
        try {
            List<CompletedCourseDto> list = studentService.setCompletedCoursesBulk(studentId, courseIds);
            return ResponseEntity.ok(list);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{studentId}/eligibility")
    public ResponseEntity<List<CourseEligibilityDto>> checkEligibility(@PathVariable Long studentId) {
        return ResponseEntity.ok(studentService.checkEligibility(studentId));
    }

    @GetMapping("/{studentId}/course-path")
    public ResponseEntity<PathSummaryDto> getCoursePathSummary(@PathVariable Long studentId) {
        return ResponseEntity.ok(studentService.generateCoursePathSummary(studentId));
    }
}
