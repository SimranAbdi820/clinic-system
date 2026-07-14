package com.clinic.clinic_backend.controller;

import com.clinic.clinic_backend.dto.ApiResponse;
import com.clinic.clinic_backend.entity.Patient;
import com.clinic.clinic_backend.entity.User;
import com.clinic.clinic_backend.repository.PatientRepository;
import com.clinic.clinic_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/Patient")
@CrossOrigin(origins = "*")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ApiResponse<List<Patient>> getAll() {
        return new ApiResponse<>(true, patientRepository.findAll(), null);
    }

    @GetMapping("/{id}")
    public ApiResponse<List<Patient>> getById(@PathVariable Long id) {
        return patientRepository.findById(id)
                .map(p -> new ApiResponse<>(true, List.of(p), null))
                .orElse(new ApiResponse<>(false, Collections.emptyList(), "Patient not found"));
    }

    @PostMapping
    public ApiResponse<Patient> create(@RequestBody Map<String, Object> body) {
        try {
            Patient patient = mapToPatient(body);
            return new ApiResponse<>(true, patientRepository.save(patient), null);
        } catch (Exception e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ApiResponse<Patient> update(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        try {
            Patient patient = patientRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Patient not found"));
            Patient updated = mapToPatient(body);
            updated.setId(patient.getId());
            return new ApiResponse<>(true, patientRepository.save(updated), null);
        } catch (Exception e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        try {
            patientRepository.deleteById(id);
            return new ApiResponse<>(true, null, null);
        } catch (Exception e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    private Patient mapToPatient(Map<String, Object> body) {
        Patient patient = new Patient();
        patient.setFirstName((String) body.get("firstName"));
        patient.setLastName((String) body.get("lastName"));
        patient.setGender((String) body.get("gender"));
        if (body.get("dateOfBirth") != null) {
            patient.setDateOfBirth(java.time.LocalDate.parse(body.get("dateOfBirth").toString()));
        }
        patient.setPhone((String) body.get("phone"));
        patient.setAddress((String) body.get("address"));

        if (body.get("userId") != null) {
            Long userId = Long.valueOf(body.get("userId").toString());
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            patient.setUser(user);
        }
        return patient;
    }
}