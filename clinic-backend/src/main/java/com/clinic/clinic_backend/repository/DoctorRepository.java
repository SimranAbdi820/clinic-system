package com.clinic.clinic_backend.repository;

import com.clinic.clinic_backend.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}