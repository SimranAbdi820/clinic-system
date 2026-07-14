package com.clinic.clinic_backend.controller;

import com.clinic.clinic_backend.dto.ApiResponse;
import com.clinic.clinic_backend.entity.Doctor;
import com.clinic.clinic_backend.entity.User;
import com.clinic.clinic_backend.repository.DoctorRepository;
import com.clinic.clinic_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/Doctor")
@CrossOrigin(origins = "*")
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ApiResponse<List<Doctor>> getAll() {
        return new ApiResponse<>(true, doctorRepository.findAll(), null);
    }

    @GetMapping("/{id}")
    public ApiResponse<List<Doctor>> getById(@PathVariable Long id) {
        return doctorRepository.findById(id)
                .map(d -> new ApiResponse<>(true, List.of(d), null))
                .orElse(new ApiResponse<>(false, Collections.emptyList(), "Doctor not found"));
    }

    @PostMapping
    public ApiResponse<Doctor> create(@RequestBody Map<String, Object> body) {
        try {
            return new ApiResponse<>(true, doctorRepository.save(mapToDoctor(body)), null);
        } catch (Exception e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ApiResponse<Doctor> update(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        try {
            Doctor existing = doctorRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));
            Doctor updated = mapToDoctor(body);
            updated.setId(existing.getId());
            return new ApiResponse<>(true, doctorRepository.save(updated), null);
        } catch (Exception e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        try {
            doctorRepository.deleteById(id);
            return new ApiResponse<>(true, null, null);
        } catch (Exception e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    private Doctor mapToDoctor(Map<String, Object> body) {
        Doctor doctor = new Doctor();
        doctor.setFirstName((String) body.get("firstName"));
        doctor.setLastName((String) body.get("lastName"));
        doctor.setSpecialty((String) body.get("specialty"));
        doctor.setPhone((String) body.get("phone"));
        doctor.setEmail((String) body.get("email"));

        if (body.get("userId") != null) {
            Long userId = Long.valueOf(body.get("userId").toString());
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            doctor.setUser(user);
        }
        return doctor;
    }
}