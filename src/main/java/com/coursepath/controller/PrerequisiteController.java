package com.coursepath.controller;

import com.coursepath.dto.PrerequisiteDto;
import com.coursepath.service.PrerequisiteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prerequisites")
public class PrerequisiteController {

    private final PrerequisiteService prerequisiteService;

    public PrerequisiteController(PrerequisiteService prerequisiteService) {
        this.prerequisiteService = prerequisiteService;
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<PrerequisiteDto>> getPrerequisitesForCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(prerequisiteService.getPrerequisitesForCourse(courseId));
    }

    @PostMapping
    public ResponseEntity<?> addPrerequisite(@RequestBody PrerequisiteDto dto) {
        try {
            PrerequisiteDto created = prerequisiteService.addPrerequisite(
                    dto.getCourseId(),
                    dto.getPrerequisiteCourseId(),
                    dto.getRequirementType(),
                    dto.getMinGrade()
            );
            return ResponseEntity.ok(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removePrerequisite(@PathVariable Long id) {
        try {
            prerequisiteService.removePrerequisite(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to remove prerequisite: " + e.getMessage());
        }
    }
}
