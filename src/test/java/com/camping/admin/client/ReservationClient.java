package com.camping.admin.client;

import com.camping.admin.CommonContext;
import com.camping.admin.dto.ReservationResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;

public class ReservationClient {
    public ReservationResponse getReservation(Long reservationId) {
        ExtractableResponse<Response> extracted = given().log().all()
            .contentType("application/json")
            .header("Authorization", "Bearer " + CommonContext.accessToken)
            .when().log().all()
            .get("admin/reservations")
            .then().log().all()
            .statusCode(200)
            .extract();

        List<ReservationResponse> resList = extracted.body().jsonPath().getList("", ReservationResponse.class);

        Optional<ReservationResponse> maybeReservation = resList.stream()
            .filter(r -> r.getId().equals(CommonContext.reservationId))
            .findFirst();

        if(maybeReservation.isPresent()) {
            return maybeReservation.get();
        } else {
            throw new RuntimeException("예약이 존재하지 않습니다.");
        }
    }

    public List<ReservationResponse> getReservations() {
        ExtractableResponse<Response> extracted = given().log().all()
            .contentType("application/json")
            .header("Authorization", "Bearer " + CommonContext.accessToken)
            .when().log().all()
            .get("admin/reservations")
            .then().log().all()
            .statusCode(200)
            .extract();

        return extracted.body().jsonPath().getList("", ReservationResponse.class);
    }
}
