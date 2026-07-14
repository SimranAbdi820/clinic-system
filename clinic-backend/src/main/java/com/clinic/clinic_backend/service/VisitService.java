package com.clinic.clinic_backend.service;

import com.clinic.clinic_backend.entity.Doctor;
import com.clinic.clinic_backend.entity.Patient;
import com.clinic.clinic_backend.entity.Visit;
import com.clinic.clinic_backend.repository.DoctorRepository;
import com.clinic.clinic_backend.repository.PatientRepository;
import com.clinic.clinic_backend.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitService {

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    public List<Visit> getAllVisits() {
        return visitRepository.findAll();
    }

    public Visit getVisitById(Long id) {
        return visitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visit not found with id: " + id));
    }

    public Visit createVisit(Long patientId, Long doctorId, String diagnosis) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        Visit visit = new Visit();
        visit.setPatient(patient);
        visit.setDoctor(doctor);
        visit.setDiagnosis(diagnosis);
        return visitRepository.save(visit);
    }

    public Visit updateVisit(Long id, String diagnosis) {
        Visit visit = getVisitById(id);
        visit.setDiagnosis(diagnosis);
        return visitRepository.save(visit);
    }

    public void deleteVisit(Long id) {
        visitRepository.deleteById(id);
    }
}