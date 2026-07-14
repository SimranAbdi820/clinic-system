package com.clinic.clinic_backend.controller;

import com.clinic.clinic_backend.dto.ApiResponse;
import com.clinic.clinic_backend.entity.Doctor;
import com.clinic.clinic_backend.entity.Patient;
import com.clinic.clinic_backend.entity.Visit;
import com.clinic.clinic_backend.repository.DoctorRepository;
import com.clinic.clinic_backend.repository.PatientRepository;
import com.clinic.clinic_backend.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/Visit")
@CrossOrigin(origins = "*")
public class VisitController {

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping
    public ApiResponse<List<Visit>> getAll() {
        return new ApiResponse<>(true, visitRepository.findAll(), null);
    }

    @GetMapping("/{id}")
    public ApiResponse<List<Visit>> getById(@PathVariable Long id) {
        return visitRepository.findById(id)
                .map(v -> new ApiResponse<>(true, List.of(v), null))
                .orElse(new ApiResponse<>(false, Collections.emptyList(), "Visit not found"));
    }

    @PostMapping
    public ApiResponse<Visit> create(@RequestBody Map<String, Object> body) {
        try {
            return new ApiResponse<>(true, visitRepository.save(mapToVisit(body, null)), null);
        } catch (Exception e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ApiResponse<Visit> update(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        try {
            Visit existing = visitRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Visit not found"));
            Visit updated = mapToVisit(body, existing.getVisitDate());
            updated.setId(existing.getId());
            return new ApiResponse<>(true, visitRepository.save(updated), null);
        } catch (Exception e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        try {
            visitRepository.deleteById(id);
            return new ApiResponse<>(true, null, null);
        } catch (Exception e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    private Visit mapToVisit(Map<String, Object> body, LocalDateTime existingDate) {
        Visit visit = new Visit();

        Long patientId = Long.valueOf(body.get("patientId").toString());
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        visit.setPatient(patient);

        Long doctorId = Long.valueOf(body.get("doctorId").toString());
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        visit.setDoctor(doctor);

        visit.setDiagnosis((String) body.get("diagnosis"));

        if (body.get("visitDate") != null && !body.get("visitDate").toString().isBlank()) {
            visit.setVisitDate(java.time.LocalDate.parse(body.get("visitDate").toString()).atStartOfDay());
        } else if (existingDate != null) {
            visit.setVisitDate(existingDate);
        }

        return visit;
    }
}