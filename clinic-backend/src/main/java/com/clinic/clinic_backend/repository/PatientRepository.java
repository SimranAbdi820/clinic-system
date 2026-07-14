package com.clinic.clinic_backend.repository;

import com.clinic.clinic_backend.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}