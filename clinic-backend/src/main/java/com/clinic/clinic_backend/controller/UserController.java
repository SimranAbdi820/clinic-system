package com.clinic.clinic_backend.controller;

import com.clinic.clinic_backend.dto.ApiResponse;
import com.clinic.clinic_backend.entity.User;
import com.clinic.clinic_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ApiResponse<List<User>> getAll() {
        return new ApiResponse<>(true, userRepository.findAll(), null);
    }

    @GetMapping("/{id}")
    public ApiResponse<User> getById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(u -> new ApiResponse<>(true, u, (String) null))
                .orElse(new ApiResponse<>(false, null, "User not found"));
    }

    @PutMapping("/{id}")
    public ApiResponse<User> update(@PathVariable Long id, @RequestBody User body) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            user.setUsername(body.getUsername());
            user.setEmail(body.getEmail());
            if (body.getPasswordHash() != null && !body.getPasswordHash().isBlank()) {
                user.setPasswordHash(body.getPasswordHash());
            }
            return new ApiResponse<>(true, userRepository.save(user), null);
        } catch (Exception e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        userRepository.deleteById(id);
        return new ApiResponse<>(true, null, null);
    }
}