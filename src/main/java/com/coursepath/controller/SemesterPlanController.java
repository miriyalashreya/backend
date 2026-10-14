package com.coursepath.controller;

import com.coursepath.dto.SemesterPlanRequest;
import com.coursepath.dto.SemesterPlanResponseDto;
import com.coursepath.service.SemesterPlanService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SemesterPlanController {

    private final SemesterPlanService semesterPlanService;

    public SemesterPlanController(SemesterPlanService semesterPlanService) {
        this.semesterPlanService = semesterPlanService;
    }

    @GetMapping("/student/{studentId}/plans")
    public ResponseEntity<List<SemesterPlanResponseDto>> getStudentPlans(@PathVariable Long studentId) {
        return ResponseEntity.ok(semesterPlanService.getStudentPlans(studentId));
    }

    @PostMapping("/student/{studentId}/plans")
    public ResponseEntity<?> createSemesterPlan(@PathVariable Long studentId, @Valid @RequestBody SemesterPlanRequest request) {
        try {
            SemesterPlanResponseDto plan = semesterPlanService.createPlan(studentId, request);
            return ResponseEntity.ok(plan);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/plans/{planId}")
    public ResponseEntity<?> deletePlan(@PathVariable Long planId) {
        try {
            semesterPlanService.deletePlan(planId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete plan: " + e.getMessage());
        }
    }
}
