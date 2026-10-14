package com.coursepath.initializer;

import com.coursepath.model.*;
import com.coursepath.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataInitializer implements CommandLineRunner {

    private final DepartmentRepository departmentRepository;
    private final CourseRepository courseRepository;
    private final PrerequisiteRepository prerequisiteRepository;
    private final UserRepository userRepository;
    private final StudentCompletedCourseRepository completedCourseRepository;

    public DataInitializer(DepartmentRepository departmentRepository,
                           CourseRepository courseRepository,
                           PrerequisiteRepository prerequisiteRepository,
                           UserRepository userRepository,
                           StudentCompletedCourseRepository completedCourseRepository) {
        this.departmentRepository = departmentRepository;
        this.courseRepository = courseRepository;
        this.prerequisiteRepository = prerequisiteRepository;
        this.userRepository = userRepository;
        this.completedCourseRepository = completedCourseRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (departmentRepository.count() > 0) {
            return; // Data already seeded
        }

        System.out.println(">>> Initializing CoursePath Academic Curriculum Data...");

        // 1. Create Departments
        Department cse = departmentRepository.save(new Department("CSE", "Computer Science & Engineering", "Core curriculum covering software engineering, algorithms, databases, and AI."));
        Department ece = departmentRepository.save(new Department("ECE", "Electronics & Communication Engineering", "Hardware, signal processing, and embedded systems."));
        Department math = departmentRepository.save(new Department("MATH", "Department of Mathematics", "Pure and applied mathematics foundation courses."));

        // 2. Create Courses Map
        Map<String, Course> cMap = new HashMap<>();

        // Semester 1
        cMap.put("CS101", courseRepository.save(new Course("CS101", "Programming Fundamentals & C", "Foundations of procedural programming, variables, control flow, functions, and memory management.", 4, 1, cse)));
        cMap.put("MATH101", courseRepository.save(new Course("MATH101", "Calculus & Linear Algebra", "Differential and integral calculus, vector spaces, eigenvalues, and matrices.", 4, 1, math)));
        cMap.put("PHY101", courseRepository.save(new Course("PHY101", "Engineering Physics", "Mechanics, wave optics, electromagnetism, and modern physics principles.", 3, 1, cse)));
        cMap.put("ENG101", courseRepository.save(new Course("ENG101", "Technical Communication", "Professional writing, presentation skills, and engineering documentation.", 2, 1, cse)));

        // Semester 2
        cMap.put("CS102", courseRepository.save(new Course("CS102", "Object Oriented Programming (Java)", "Classes, objects, inheritance, polymorphism, encapsulation, exception handling, and collections.", 4, 2, cse)));
        cMap.put("MATH102", courseRepository.save(new Course("MATH102", "Discrete Mathematics & Logic", "Set theory, propositional logic, graph theory, combinatorics, and proof techniques.", 4, 2, math)));
        cMap.put("CS103", courseRepository.save(new Course("CS103", "Digital Logic & Computer Organization", "Boolean algebra, logic gates, combinational and sequential circuits, and CPU basics.", 3, 2, cse)));
        cMap.put("EVS101", courseRepository.save(new Course("EVS101", "Environmental Studies & Ecology", "Environmental science, sustainability, ecosystems, and conservation.", 2, 2, cse)));

        // Semester 3
        cMap.put("CS201", courseRepository.save(new Course("CS201", "Data Structures & Applications", "Arrays, linked lists, stacks, queues, trees, heaps, hash tables, and graphs.", 4, 3, cse)));
        cMap.put("CS202", courseRepository.save(new Course("CS202", "Database Management Systems", "Relational algebra, SQL, normalization, transactions, ACID properties, and indexing.", 4, 3, cse)));
        cMap.put("CS203", courseRepository.save(new Course("CS203", "Computer Architecture", "Instruction set architectures, pipelining, cache memory hierarchies, and I/O systems.", 3, 3, cse)));
        cMap.put("MATH201", courseRepository.save(new Course("MATH201", "Probability & Applied Statistics", "Probability distributions, hypothesis testing, regression analysis, and random variables.", 3, 3, math)));

        // Semester 4
        cMap.put("CS204", courseRepository.save(new Course("CS204", "Design & Analysis of Algorithms", "Divide and conquer, greedy algorithms, dynamic programming, graph algorithms, and NP-completeness.", 4, 4, cse)));
        cMap.put("CS205", courseRepository.save(new Course("CS205", "Operating Systems", "Process scheduling, thread synchronization, deadlocks, memory management, and file systems.", 4, 4, cse)));
        cMap.put("CS206", courseRepository.save(new Course("CS206", "Software Engineering & Agile", "Software lifecycle models, requirements engineering, design patterns, testing, and CI/CD.", 3, 4, cse)));
        cMap.put("CS207", courseRepository.save(new Course("CS207", "Web Technologies & Full Stack", "HTML5, CSS, modern JavaScript, RESTful APIs, and full stack application patterns.", 3, 4, cse)));

        // Semester 5
        cMap.put("CS301", courseRepository.save(new Course("CS301", "Computer Networks", "OSI & TCP/IP models, routing protocols, transport layer flow control, and network security.", 4, 5, cse)));
        cMap.put("CS302", courseRepository.save(new Course("CS302", "Theory of Computation & Automata", "Finite automata, regular expressions, context-free grammars, Turing machines, and decodability.", 3, 5, cse)));
        cMap.put("CS303", courseRepository.save(new Course("CS303", "Artificial Intelligence", "State space search, heuristics, game playing, knowledge representation, and probabilistic reasoning.", 4, 5, cse)));
        cMap.put("CS304", courseRepository.save(new Course("CS304", "Cloud Computing & Distributed Storage", "Cloud service models (IaaS, PaaS, SaaS), virtualization, containers, and distributed object stores.", 3, 5, cse)));

        // Semester 6
        cMap.put("CS305", courseRepository.save(new Course("CS305", "Machine Learning", "Supervised and unsupervised learning, decision trees, SVM, clustering, and model evaluation.", 4, 6, cse)));
        cMap.put("CS306", courseRepository.save(new Course("CS306", "Compiler Design", "Lexical analysis, syntax analysis, parsing techniques, intermediate code, and code generation.", 4, 6, cse)));
        cMap.put("CS307", courseRepository.save(new Course("CS307", "Network & Information Security", "Cryptography, public key infrastructure, network firewalls, penetration testing, and security policies.", 3, 6, cse)));
        cMap.put("CS308", courseRepository.save(new Course("CS308", "Mobile Application Development", "Mobile app architectures, native and cross-platform UI, state management, and device APIs.", 3, 6, cse)));

        // Semester 7
        cMap.put("CS401", courseRepository.save(new Course("CS401", "Deep Learning & Neural Networks", "Backpropagation, convolutional neural networks (CNNs), recurrent neural networks (RNNs), and transformers.", 4, 7, cse)));
        cMap.put("CS402", courseRepository.save(new Course("CS402", "Distributed Systems", "Consensus algorithms, Paxos, Raft, vector clocks, RPC, and fault tolerance.", 3, 7, cse)));
        cMap.put("CS403", courseRepository.save(new Course("CS403", "DevOps & Microservices", "Microservice design patterns, API gateways, Docker, Kubernetes, and automated deployment pipelines.", 3, 7, cse)));
        cMap.put("CS498", courseRepository.save(new Course("CS498", "Capstone Project Phase 1", "Comprehensive engineering project ideation, literature survey, requirements analysis, and system architecture.", 4, 7, cse)));

        // Semester 8
        cMap.put("CS499", courseRepository.save(new Course("CS499", "Capstone Project Phase 2", "System implementation, testing, deployment, evaluation, technical report, and oral defense.", 8, 8, cse)));
        cMap.put("CS405", courseRepository.save(new Course("CS405", "Ethics & Tech Law", "Professional engineering ethics, software licensing, intellectual property rights, and AI governance.", 2, 8, cse)));

        // 3. Add Prerequisite Relationships (Target requires Prerequisite)
        // Sem 2 prereqs
        addPrereq(cMap.get("CS102"), cMap.get("CS101"), RequirementType.MANDATORY, "C");
        addPrereq(cMap.get("MATH102"), cMap.get("MATH101"), RequirementType.MANDATORY, "C");

        // Sem 3 prereqs
        addPrereq(cMap.get("CS201"), cMap.get("CS102"), RequirementType.MANDATORY, "C");
        addPrereq(cMap.get("CS202"), cMap.get("CS101"), RequirementType.MANDATORY, "C");
        addPrereq(cMap.get("CS203"), cMap.get("CS103"), RequirementType.MANDATORY, "C");
        addPrereq(cMap.get("MATH201"), cMap.get("MATH102"), RequirementType.MANDATORY, "C");

        // Sem 4 prereqs
        addPrereq(cMap.get("CS204"), cMap.get("CS201"), RequirementType.MANDATORY, "C");
        addPrereq(cMap.get("CS205"), cMap.get("CS201"), RequirementType.MANDATORY, "C");
        addPrereq(cMap.get("CS206"), cMap.get("CS102"), RequirementType.MANDATORY, "C");
        addPrereq(cMap.get("CS207"), cMap.get("CS202"), RequirementType.MANDATORY, "C");

        // Sem 5 prereqs
        addPrereq(cMap.get("CS301"), cMap.get("CS205"), RequirementType.MANDATORY, "C");
        addPrereq(cMap.get("CS302"), cMap.get("MATH102"), RequirementType.MANDATORY, "C");
        addPrereq(cMap.get("CS302"), cMap.get("CS204"), RequirementType.MANDATORY, "C");
        addPrereq(cMap.get("CS303"), cMap.get("CS204"), RequirementType.MANDATORY, "C");
        addPrereq(cMap.get("CS304"), cMap.get("CS205"), RequirementType.MANDATORY, "C");

        // Sem 6 prereqs
        addPrereq(cMap.get("CS305"), cMap.get("CS303"), RequirementType.MANDATORY, "C");
        addPrereq(cMap.get("CS305"), cMap.get("MATH201"), RequirementType.MANDATORY, "C");
        addPrereq(cMap.get("CS306"), cMap.get("CS302"), RequirementType.MANDATORY, "C");
        addPrereq(cMap.get("CS307"), cMap.get("CS301"), RequirementType.MANDATORY, "C");
        addPrereq(cMap.get("CS308"), cMap.get("CS207"), RequirementType.MANDATORY, "C");

        // Sem 7 prereqs
        addPrereq(cMap.get("CS401"), cMap.get("CS305"), RequirementType.MANDATORY, "B");
        addPrereq(cMap.get("CS402"), cMap.get("CS301"), RequirementType.MANDATORY, "C");
        addPrereq(cMap.get("CS402"), cMap.get("CS205"), RequirementType.MANDATORY, "C");
        addPrereq(cMap.get("CS403"), cMap.get("CS304"), RequirementType.MANDATORY, "C");
        addPrereq(cMap.get("CS498"), cMap.get("CS206"), RequirementType.MANDATORY, "C");

        // Sem 8 prereqs
        addPrereq(cMap.get("CS499"), cMap.get("CS498"), RequirementType.MANDATORY, "C");

        // 4. Create Users
        User admin = new User("admin", "admin123", "Dr. Alan Turing", "admin@coursepath.edu", "ADM-001", Role.ADMIN, cse, null);
        userRepository.save(admin);

        User student = new User("student", "student123", "John Doe", "john.doe@coursepath.edu", "STU-2023-042", Role.STUDENT, cse, 3);
        student = userRepository.save(student);

        User alice = new User("alice", "alice123", "Alice Smith", "alice.smith@coursepath.edu", "STU-2024-101", Role.STUDENT, cse, 1);
        userRepository.save(alice);

        // 5. Populate John Doe's Completed Courses (Semesters 1 & 2 fully completed!)
        addCompleted(student, cMap.get("CS101"), 1, "A");
        addCompleted(student, cMap.get("MATH101"), 1, "A-");
        addCompleted(student, cMap.get("PHY101"), 1, "B+");
        addCompleted(student, cMap.get("ENG101"), 1, "A");

        addCompleted(student, cMap.get("CS102"), 2, "A");
        addCompleted(student, cMap.get("MATH102"), 2, "B+");
        addCompleted(student, cMap.get("CS103"), 2, "A");
        addCompleted(student, cMap.get("EVS101"), 2, "A");

        System.out.println(">>> CoursePath initial data loaded successfully! 24 courses, prerequisites, and demo accounts ready.");
    }

    private void addPrereq(Course target, Course prereq, RequirementType type, String minGrade) {
        if (target != null && prereq != null) {
            Prerequisite p = new Prerequisite(target, prereq, type, minGrade);
            prerequisiteRepository.save(p);
        }
    }

    private void addCompleted(User student, Course course, int sem, String grade) {
        if (student != null && course != null) {
            StudentCompletedCourse sc = new StudentCompletedCourse(student, course, sem, grade);
            sc.setCompletionDate(LocalDate.now().minusMonths(6));
            completedCourseRepository.save(sc);
        }
    }
}
