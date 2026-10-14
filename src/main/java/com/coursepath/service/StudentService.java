package com.coursepath.service;

import com.coursepath.dto.*;
import com.coursepath.model.*;
import com.coursepath.repository.CourseRepository;
import com.coursepath.repository.PrerequisiteRepository;
import com.coursepath.repository.StudentCompletedCourseRepository;
import com.coursepath.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentCompletedCourseRepository completedCourseRepository;
    private final CourseRepository courseRepository;
    private final PrerequisiteRepository prerequisiteRepository;
    private final UserRepository userRepository;
    private final CourseService courseService;

    public StudentService(StudentCompletedCourseRepository completedCourseRepository,
                          CourseRepository courseRepository,
                          PrerequisiteRepository prerequisiteRepository,
                          UserRepository userRepository,
                          CourseService courseService) {
        this.completedCourseRepository = completedCourseRepository;
        this.courseRepository = courseRepository;
        this.prerequisiteRepository = prerequisiteRepository;
        this.userRepository = userRepository;
        this.courseService = courseService;
    }

    public List<CompletedCourseDto> getCompletedCourses(Long studentId) {
        List<StudentCompletedCourse> completedList = completedCourseRepository.findByStudentId(studentId);
        return completedList.stream().map(cc -> {
            CompletedCourseDto dto = new CompletedCourseDto();
            dto.setId(cc.getId());
            dto.setCourseId(cc.getCourse().getId());
            dto.setCourseCode(cc.getCourse().getCode());
            dto.setCourseTitle(cc.getCourse().getTitle());
            dto.setCredits(cc.getCourse().getCredits());
            dto.setSemester(cc.getCourse().getSemester());
            dto.setSemesterCompleted(cc.getSemesterCompleted());
            dto.setGrade(cc.getGrade());
            dto.setCompletionDate(cc.getCompletionDate());
            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional
    public CompletedCourseDto markCourseCompleted(Long studentId, Long courseId, Integer semesterCompleted, String grade) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + courseId));

        Optional<StudentCompletedCourse> existingOpt = completedCourseRepository.findByStudentIdAndCourseId(studentId, courseId);
        StudentCompletedCourse completedCourse;
        if (existingOpt.isPresent()) {
            completedCourse = existingOpt.get();
            completedCourse.setSemesterCompleted(semesterCompleted != null ? semesterCompleted : completedCourse.getSemesterCompleted());
            completedCourse.setGrade(grade != null ? grade : completedCourse.getGrade());
        } else {
            completedCourse = new StudentCompletedCourse();
            completedCourse.setStudent(student);
            completedCourse.setCourse(course);
            completedCourse.setSemesterCompleted(semesterCompleted != null ? semesterCompleted : course.getSemester());
            completedCourse.setGrade(grade != null && !grade.isBlank() ? grade : "A");
            completedCourse.setCompletionDate(LocalDate.now());
        }

        StudentCompletedCourse saved = completedCourseRepository.save(completedCourse);
        return new CompletedCourseDto(
                saved.getId(),
                course.getId(),
                course.getCode(),
                course.getTitle(),
                course.getCredits(),
                course.getSemester(),
                saved.getSemesterCompleted(),
                saved.getGrade(),
                saved.getCompletionDate()
        );
    }

    @Transactional
    public void removeCompletedCourse(Long studentId, Long courseId) {
        completedCourseRepository.deleteByStudentIdAndCourseId(studentId, courseId);
    }

    @Transactional
    public List<CompletedCourseDto> setCompletedCoursesBulk(Long studentId, List<Long> courseIds) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found: " + studentId));

        // Get currently completed courses to preserve existing grades
        Map<Long, StudentCompletedCourse> existingMap = completedCourseRepository.findByStudentId(studentId).stream()
                .collect(Collectors.toMap(c -> c.getCourse().getId(), c -> c));

        completedCourseRepository.deleteByStudentId(studentId);

        List<StudentCompletedCourse> toSave = new ArrayList<>();
        for (Long cId : courseIds) {
            Optional<Course> courseOpt = courseRepository.findById(cId);
            if (courseOpt.isPresent()) {
                Course course = courseOpt.get();
                StudentCompletedCourse cc = new StudentCompletedCourse();
                cc.setStudent(student);
                cc.setCourse(course);
                if (existingMap.containsKey(cId)) {
                    cc.setGrade(existingMap.get(cId).getGrade());
                    cc.setSemesterCompleted(existingMap.get(cId).getSemesterCompleted());
                } else {
                    cc.setGrade("A");
                    cc.setSemesterCompleted(course.getSemester());
                }
                cc.setCompletionDate(LocalDate.now());
                toSave.add(cc);
            }
        }

        completedCourseRepository.saveAll(toSave);
        return getCompletedCourses(studentId);
    }

    public List<CourseEligibilityDto> checkEligibility(Long studentId) {
        List<Course> allCourses = courseRepository.findAllByOrderBySemesterAscCodeAsc();
        List<StudentCompletedCourse> completedList = completedCourseRepository.findByStudentId(studentId);

        Map<Long, StudentCompletedCourse> completedMap = completedList.stream()
                .collect(Collectors.toMap(cc -> cc.getCourse().getId(), cc -> cc));

        List<CourseEligibilityDto> result = new ArrayList<>();

        for (Course course : allCourses) {
            CourseEligibilityDto dto = new CourseEligibilityDto();
            dto.setCourse(courseService.convertToDto(course));

            if (completedMap.containsKey(course.getId())) {
                StudentCompletedCourse completedRecord = completedMap.get(course.getId());
                dto.setStatus("COMPLETED");
                dto.setCompleted(true);
                dto.setEligible(false);
                dto.setGradeEarned(completedRecord.getGrade());
                dto.setSemesterCompleted(completedRecord.getSemesterCompleted());
                dto.setReason("Course already completed with grade " + completedRecord.getGrade());
            } else {
                List<Prerequisite> prereqs = prerequisiteRepository.findByCourseId(course.getId());
                List<CourseDto> missingPrereqs = new ArrayList<>();
                List<CourseDto> satisfiedPrereqs = new ArrayList<>();

                boolean allMandatorySatisfied = true;
                for (Prerequisite prereq : prereqs) {
                    Course prereqCourse = prereq.getPrerequisiteCourse();
                    boolean isPrereqDone = completedMap.containsKey(prereqCourse.getId());

                    if (isPrereqDone) {
                        satisfiedPrereqs.add(courseService.convertToDto(prereqCourse));
                    } else {
                        if (prereq.getRequirementType() == RequirementType.MANDATORY) {
                            allMandatorySatisfied = false;
                            missingPrereqs.add(courseService.convertToDto(prereqCourse));
                        }
                    }
                }

                dto.setMissingPrerequisites(missingPrereqs);
                dto.setSatisfiedPrerequisites(satisfiedPrereqs);

                if (allMandatorySatisfied) {
                    dto.setStatus("ELIGIBLE");
                    dto.setCompleted(false);
                    dto.setEligible(true);
                    if (prereqs.isEmpty()) {
                        dto.setReason("No prerequisites required - Open for enrollment");
                    } else {
                        dto.setReason("All " + prereqs.size() + " prerequisite(s) satisfied");
                    }
                } else {
                    dto.setStatus("LOCKED");
                    dto.setCompleted(false);
                    dto.setEligible(false);
                    String missingNames = missingPrereqs.stream().map(CourseDto::getCode).collect(Collectors.joining(", "));
                    dto.setReason("Prerequisite not met: Missing " + missingNames);
                }
            }

            result.add(dto);
        }

        return result;
    }

    public PathSummaryDto generateCoursePathSummary(Long studentId) {
        List<CourseEligibilityDto> eligibilityList = checkEligibility(studentId);
        List<StudentCompletedCourse> completedList = completedCourseRepository.findByStudentId(studentId);

        PathSummaryDto summary = new PathSummaryDto();
        summary.setTotalDegreeCourses(eligibilityList.size());
        summary.setAllCourses(eligibilityList);

        int totalCredits = eligibilityList.stream().mapToInt(e -> e.getCourse().getCredits()).sum();
        summary.setTotalDegreeCredits(totalCredits);

        int completedCredits = 0;
        int completedCount = 0;
        int eligibleCount = 0;
        int lockedCount = 0;
        double totalQualityPoints = 0.0;

        List<CourseEligibilityDto> eligibleNextList = new ArrayList<>();

        for (CourseEligibilityDto c : eligibilityList) {
            if ("COMPLETED".equals(c.getStatus())) {
                completedCount++;
                int credits = c.getCourse().getCredits();
                completedCredits += credits;
                totalQualityPoints += gradeToPoints(c.getGradeEarned()) * credits;
            } else if ("ELIGIBLE".equals(c.getStatus())) {
                eligibleCount++;
                eligibleNextList.add(c);
            } else if ("LOCKED".equals(c.getStatus())) {
                lockedCount++;
            }
        }

        summary.setCompletedCoursesCount(completedCount);
        summary.setEligibleCoursesCount(eligibleCount);
        summary.setLockedCoursesCount(lockedCount);
        summary.setCompletedCredits(completedCredits);

        double progress = totalCredits > 0 ? ((double) completedCredits / totalCredits) * 100.0 : 0.0;
        summary.setProgressPercentage(Math.round(progress * 10.0) / 10.0);

        double gpa = completedCredits > 0 ? (totalQualityPoints / completedCredits) : 0.0;
        summary.setCumulativeGpa(Math.round(gpa * 100.0) / 100.0);

        // Sort eligible next courses by semester
        eligibleNextList.sort(Comparator.comparingInt(a -> a.getCourse().getSemester()));
        summary.setEligibleNextCourses(eligibleNextList);

        // Group into 8 Semesters Roadmap
        Map<Integer, List<CourseEligibilityDto>> groupedBySemester = eligibilityList.stream()
                .collect(Collectors.groupingBy(c -> c.getCourse().getSemester()));

        List<SemesterPathGroupDto> roadmap = new ArrayList<>();
        for (int sem = 1; sem <= 8; sem++) {
            List<CourseEligibilityDto> semCourses = groupedBySemester.getOrDefault(sem, Collections.emptyList());
            SemesterPathGroupDto group = new SemesterPathGroupDto(sem, "Semester " + sem);
            group.setCourses(semCourses);
            group.setTotalCourses(semCourses.size());
            group.setTotalCredits(semCourses.stream().mapToInt(c -> c.getCourse().getCredits()).sum());

            int comp = 0, elig = 0, lock = 0;
            for (CourseEligibilityDto item : semCourses) {
                if ("COMPLETED".equals(item.getStatus())) comp++;
                else if ("ELIGIBLE".equals(item.getStatus())) elig++;
                else if ("LOCKED".equals(item.getStatus())) lock++;
            }
            group.setCompletedCourses(comp);
            group.setEligibleCourses(elig);
            group.setLockedCourses(lock);

            roadmap.add(group);
        }

        summary.setSemesterRoadmap(roadmap);
        return summary;
    }

    private double gradeToPoints(String grade) {
        if (grade == null) return 4.0;
        switch (grade.trim().toUpperCase()) {
            case "A+":
            case "A": return 4.0;
            case "A-": return 3.7;
            case "B+": return 3.3;
            case "B": return 3.0;
            case "B-": return 2.7;
            case "C+": return 2.3;
            case "C": return 2.0;
            case "D": return 1.0;
            default: return 3.5;
        }
    }
}
