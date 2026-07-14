package com.clinic.clinic_backend.controller;

import com.clinic.clinic_backend.dto.ApiResponse;
import com.clinic.clinic_backend.entity.Bill;
import com.clinic.clinic_backend.entity.Visit;
import com.clinic.clinic_backend.repository.BillRepository;
import com.clinic.clinic_backend.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/Bill")
@CrossOrigin(origins = "*")
public class BillController {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private VisitRepository visitRepository;

    @GetMapping
    public ApiResponse<List<Bill>> getAll() {
        return new ApiResponse<>(true, billRepository.findAll(), null);
    }

    @GetMapping("/{id}")
    public ApiResponse<List<Bill>> getById(@PathVariable Long id) {
        return billRepository.findById(id)
                .map(b -> new ApiResponse<>(true, List.of(b), null))
                .orElse(new ApiResponse<>(false, Collections.emptyList(), "Bill not found"));
    }

    @PostMapping
    public ApiResponse<Bill> create(@RequestBody Map<String, Object> body) {
        try {
            return new ApiResponse<>(true, billRepository.save(mapToBill(body)), null);
        } catch (Exception e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ApiResponse<Bill> update(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        try {
            Bill existing = billRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Bill not found"));
            Bill updated = mapToBill(body);
            updated.setId(existing.getId());
            return new ApiResponse<>(true, billRepository.save(updated), null);
        } catch (Exception e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        try {
            billRepository.deleteById(id);
            return new ApiResponse<>(true, null, null);
        } catch (Exception e) {
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    private Bill mapToBill(Map<String, Object> body) {
        Bill bill = new Bill();

        Long visitId = Long.valueOf(body.get("visitId").toString());
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new RuntimeException("Visit not found"));
        bill.setVisit(visit);

        bill.setAmount(new BigDecimal(body.get("amount").toString()));
        bill.setPaymentStatus((String) body.get("paymentStatus"));

        if (body.get("billDate") != null && !body.get("billDate").toString().isBlank()) {
            bill.setBillDate(java.time.LocalDate.parse(body.get("billDate").toString()).atStartOfDay());
        }

        return bill;
    }
}