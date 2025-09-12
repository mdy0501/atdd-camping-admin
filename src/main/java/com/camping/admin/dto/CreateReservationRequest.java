package com.camping.admin.dto;

import lombok.*;

import java.time.LocalDate;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateReservationRequest {
    private String customerName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long campsiteId;
    private String siteNumber;
    private String phoneNumber;
    private LocalDate reservationDate;
}
