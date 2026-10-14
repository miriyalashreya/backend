package com.coursepath;

import com.coursepath.dto.CourseDto;
import com.coursepath.model.Department;
import com.coursepath.model.RequirementType;
import com.coursepath.repository.DepartmentRepository;
import com.coursepath.service.CourseService;
import com.coursepath.service.PrerequisiteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PrerequisiteServiceTests {

    @Autowired
    private PrerequisiteService prerequisiteService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    @DisplayName("Should prevent a course from having itself as a prerequisite")
    void shouldPreventSelfPrerequisite() {
        Department dept = departmentRepository.findAll().get(0);
        CourseDto c = new CourseDto();
        c.setCode("TEST101");
        c.setTitle("Test Self Prereq");
        c.setCredits(3);
        c.setSemester(1);
        c.setDepartmentId(dept.getId());
        CourseDto saved = courseService.createCourse(c);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            prerequisiteService.addPrerequisite(saved.getId(), saved.getId(), RequirementType.MANDATORY, "C");
        });

        assertTrue(exception.getMessage().contains("itself as a prerequisite"));
    }

    @Test
    @DisplayName("Should detect and prevent circular prerequisite dependencies")
    void shouldPreventCircularDependency() {
        Department dept = departmentRepository.findAll().get(0);

        // Create Course A
        CourseDto a = new CourseDto();
        a.setCode("TESTA");
        a.setTitle("Test Course A");
        a.setCredits(3);
        a.setSemester(1);
        a.setDepartmentId(dept.getId());
        CourseDto savedA = courseService.createCourse(a);

        // Create Course B
        CourseDto b = new CourseDto();
        b.setCode("TESTB");
        b.setTitle("Test Course B");
        b.setCredits(3);
        b.setSemester(2);
        b.setDepartmentId(dept.getId());
        CourseDto savedB = courseService.createCourse(b);

        // B requires A (valid)
        prerequisiteService.addPrerequisite(savedB.getId(), savedA.getId(), RequirementType.MANDATORY, "C");

        // Now trying to make A require B should cause a cycle!
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            prerequisiteService.addPrerequisite(savedA.getId(), savedB.getId(), RequirementType.MANDATORY, "C");
        });

        assertTrue(exception.getMessage().contains("circular dependency"));
    }
}
