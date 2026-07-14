package com.clinic.clinic_backend.controller;

import com.clinic.clinic_backend.entity.Prescription;
import com.clinic.clinic_backend.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/prescriptions")
@CrossOrigin(origins = "*")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @GetMapping
    public List<Prescription> getAllPrescriptions() {
        return prescriptionService.getAllPrescriptions();
    }

    @GetMapping("/{id}")
    public Prescription getPrescriptionById(@PathVariable Long id) {
        return prescriptionService.getPrescriptionById(id);
    }

    @PostMapping
    public Prescription createPrescription(@RequestBody Map<String, Object> body) {
        Long visitId = Long.valueOf(body.get("visitId").toString());
        String medicationName = (String) body.get("medicationName");
        String dosage = (String) body.get("dosage");
        String instructions = (String) body.get("instructions");
        return prescriptionService.createPrescription(visitId, medicationName, dosage, instructions);
    }

    @PutMapping("/{id}")
    public Prescription updatePrescription(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        String medicationName = (String) body.get("medicationName");
        String dosage = (String) body.get("dosage");
        String instructions = (String) body.get("instructions");
        return prescriptionService.updatePrescription(id, medicationName, dosage, instructions);
    }

    @DeleteMapping("/{id}")
    public void deletePrescription(@PathVariable Long id) {
        prescriptionService.deletePrescription(id);
    }
}