package com.clinic.clinic_backend.service;

import com.clinic.clinic_backend.entity.Bill;
import com.clinic.clinic_backend.entity.Visit;
import com.clinic.clinic_backend.repository.BillRepository;
import com.clinic.clinic_backend.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private VisitRepository visitRepository;

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public Bill getBillById(Long id) {
        return billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found with id: " + id));
    }

    public Bill createBill(Long visitId, BigDecimal amount, String paymentStatus) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new RuntimeException("Visit not found"));

        Bill bill = new Bill();
        bill.setVisit(visit);
        bill.setAmount(amount);
        bill.setPaymentStatus(paymentStatus);
        return billRepository.save(bill);
    }

    public Bill updateBill(Long id, BigDecimal amount, String paymentStatus) {
        Bill bill = getBillById(id);
        bill.setAmount(amount);
        bill.setPaymentStatus(paymentStatus);
        return billRepository.save(bill);
    }

    public void deleteBill(Long id) {
        billRepository.deleteById(id);
    }
}