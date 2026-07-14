package com.clinic.clinic_backend.repository;

import com.clinic.clinic_backend.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
}