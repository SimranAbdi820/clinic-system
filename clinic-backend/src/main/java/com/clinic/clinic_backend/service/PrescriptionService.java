package com.clinic.clinic_backend.service;

import com.clinic.clinic_backend.entity.Prescription;
import com.clinic.clinic_backend.entity.Visit;
import com.clinic.clinic_backend.repository.PrescriptionRepository;
import com.clinic.clinic_backend.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private VisitRepository visitRepository;

    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }

    public Prescription getPrescriptionById(Long id) {
        return prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found with id: " + id));
    }

    public Prescription createPrescription(Long visitId, String medicationName, String dosage, String instructions) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new RuntimeException("Visit not found"));

        Prescription prescription = new Prescription();
        prescription.setVisit(visit);
        prescription.setMedicationName(medicationName);
        prescription.setDosage(dosage);
        prescription.setInstructions(instructions);
        return prescriptionRepository.save(prescription);
    }

    public Prescription updatePrescription(Long id, String medicationName, String dosage, String instructions) {
        Prescription prescription = getPrescriptionById(id);
        prescription.setMedicationName(medicationName);
        prescription.setDosage(dosage);
        prescription.setInstructions(instructions);
        return prescriptionRepository.save(prescription);
    }

    public void deletePrescription(Long id) {
        prescriptionRepository.deleteById(id);
    }
}