package com.coursepath;

import com.coursepath.dto.CourseEligibilityDto;
import com.coursepath.model.Course;
import com.coursepath.model.Role;
import com.coursepath.model.User;
import com.coursepath.repository.CourseRepository;
import com.coursepath.repository.UserRepository;
import com.coursepath.service.StudentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class StudentEligibilityTests {

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    @DisplayName("Should evaluate eligibility accurately based on completed prerequisites")
    void shouldEvaluateEligibilityAccurately() {
        // Create new clean test student
        User student = new User("test_student", "pass", "Test Student", "test@test.edu", "TST-001", Role.STUDENT, null, 1);
        student = userRepository.save(student);

        Course cs101 = courseRepository.findByCode("CS101").orElseThrow();
        Course cs102 = courseRepository.findByCode("CS102").orElseThrow(); // Requires CS101

        // Initially: CS101 has no prereqs -> ELIGIBLE. CS102 requires CS101 -> LOCKED.
        List<CourseEligibilityDto> initialList = studentService.checkEligibility(student.getId());

        CourseEligibilityDto cs101Dto = initialList.stream().filter(e -> e.getCourse().getCode().equals("CS101")).findFirst().orElseThrow();
        CourseEligibilityDto cs102Dto = initialList.stream().filter(e -> e.getCourse().getCode().equals("CS102")).findFirst().orElseThrow();

        assertEquals("ELIGIBLE", cs101Dto.getStatus());
        assertEquals("LOCKED", cs102Dto.getStatus());
        assertTrue(cs102Dto.getMissingPrerequisites().stream().anyMatch(p -> p.getCode().equals("CS101")));

        // Now mark CS101 as completed
        studentService.markCourseCompleted(student.getId(), cs101.getId(), 1, "A");

        // Re-check: CS101 is now COMPLETED, CS102 is now ELIGIBLE!
        List<CourseEligibilityDto> updatedList = studentService.checkEligibility(student.getId());
        CourseEligibilityDto updatedCs101 = updatedList.stream().filter(e -> e.getCourse().getCode().equals("CS101")).findFirst().orElseThrow();
        CourseEligibilityDto updatedCs102 = updatedList.stream().filter(e -> e.getCourse().getCode().equals("CS102")).findFirst().orElseThrow();

        assertEquals("COMPLETED", updatedCs101.getStatus());
        assertEquals("ELIGIBLE", updatedCs102.getStatus());
    }
}
