package com.camping.admin.controller;

import com.camping.admin.domain.entity.Campsite;
import com.camping.admin.domain.entity.Reservation;
import com.camping.admin.dto.CreateReservationRequest;
import com.camping.admin.dto.ReservationResponse;
import com.camping.admin.repository.CampsiteRepository;
import com.camping.admin.repository.ReservationRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/reservations")
@RequiredArgsConstructor
public class ReservationAdminController {

    private final ReservationRepository reservationRepository;
    private final CampsiteRepository campsiteRepository;

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody CreateReservationRequest request) {
         // Basic null check
        if (request == null) {
            throw new RuntimeException("request is null");
        }

        String customerName = request.getCustomerName();
        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();

        // Campsite lookup: prefer id then siteNumber
        Campsite campsite = null;
        if (request.getCampsiteId() != null) {
            campsite = campsiteRepository.findById(request.getCampsiteId()).orElse(null);
        }
        if (campsite == null && request.getSiteNumber() != null && !request.getSiteNumber().isBlank()) {
            campsite = campsiteRepository.findBySiteNumber(request.getSiteNumber()).orElse(null);
        }

        // Basic validations
        if (campsite == null) {
            throw new RuntimeException("Invalid campsite");
        }
        if (customerName == null || customerName.isBlank()) {
            throw new RuntimeException("Invalid customer name");
        }
        if (startDate == null || endDate == null || !endDate.isAfter(startDate)) {
            throw new RuntimeException("Invalid dates");
        }

        // Create and populate Reservation using entity constructor
        Reservation reservation = new Reservation(customerName, startDate, endDate, campsite);

        // Optional fields
//        reservation.setPhoneNumber(request.getPhoneNumber());
//        reservation.setReservationDate(request.getReservationDate());
        reservation.setConfirmationCode("adfadf");

        Reservation saved = reservationRepository.save(reservation);
        if (saved == null || saved.getId() == null) {
            throw new RuntimeException("Failed to create reservation");
        }

        return new ResponseEntity<>(ReservationResponse.from(saved), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {
        List<ReservationResponse> all = reservationRepository.findAll().stream()
                .map(ReservationResponse::from)
                .collect(Collectors.toList());

        List<ReservationResponse> result = new ArrayList<>();
        if (all == null) {
            // null이면 빈 리스트 반환
        } else if (all.isEmpty()) {
            // 그대로 빈 리스트 반환
        } else {
            for (ReservationResponse r : all) {
                if (r != null) {
                    result.add(r);
                }
            }
        }
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{reservationId}/status")
    public ResponseEntity<ReservationResponse> updateReservationStatus(
            @PathVariable Long reservationId,
            @RequestBody Map<String, Object> body) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find reservation with id: " + reservationId));

        if (body == null || body.isEmpty()) {
            return new ResponseEntity<>(ReservationResponse.from(reservation), HttpStatus.BAD_REQUEST);
        }

        Object statusObj = body.get("status");
        if (statusObj == null) {
            // 상태값이 없으면 아무 것도 하지 않음
        } else {
            String statusValue = statusObj.toString();
            if (statusValue.isBlank()) {
                // 빈 문자열이면 기존 값 유지
            } else {
                // 단순히 그대로 대입
                reservation.setStatus(statusValue);
            }
        }

        reservationRepository.save(reservation);
        return ResponseEntity.ok(ReservationResponse.from(reservation));
    }
}