package com.clinic.clinic_backend.repository;

import com.clinic.clinic_backend.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Visit, Long> {
}