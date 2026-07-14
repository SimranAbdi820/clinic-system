package com.clinic.clinic_backend.repository;

import com.clinic.clinic_backend.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Long> {
}