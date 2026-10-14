package com.coursepath.service;

import com.coursepath.dto.AuthResponse;
import com.coursepath.dto.LoginRequest;
import com.coursepath.dto.RegisterRequest;
import com.coursepath.model.Department;
import com.coursepath.model.Role;
import com.coursepath.model.User;
import com.coursepath.repository.DepartmentRepository;
import com.coursepath.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;

    public AuthService(UserRepository userRepository, DepartmentRepository departmentRepository) {
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
    }

    public AuthResponse login(LoginRequest request) {
        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());
        if (userOpt.isEmpty()) {
            return new AuthResponse(false, "Invalid username or password", null, null);
        }

        User user = userOpt.get();
        if (!user.getPassword().equals(request.getPassword())) {
            return new AuthResponse(false, "Invalid username or password", null, null);
        }

        String token = UUID.randomUUID().toString();
        return new AuthResponse(true, "Login successful", token, user);
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return new AuthResponse(false, "Username is already taken", null, null);
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            return new AuthResponse(false, "Email is already registered", null, null);
        }

        Department department = null;
        if (request.getDepartmentId() != null) {
            department = departmentRepository.findById(request.getDepartmentId()).orElse(null);
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setStudentId(request.getStudentId());
        user.setRole(request.getRole() != null ? request.getRole() : Role.STUDENT);
        user.setDepartment(department);
        user.setCurrentSemester(request.getCurrentSemester() != null ? request.getCurrentSemester() : 1);

        user = userRepository.save(user);
        String token = UUID.randomUUID().toString();
        return new AuthResponse(true, "Registration successful", token, user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
