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
        ExtractableResponse<Response> ex = given()
            .spec(CommonContext.requestSpec)
            .when().get("admin/reservations")
            .then().statusCode(200)
            .extract();

        List<ReservationResponse> list = ex.body().jsonPath().getList("", ReservationResponse.class);
        return list.stream()
            .filter(r -> r.getId().equals(reservationId))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("예약이 존재하지 않습니다."));
    }

    public List<ReservationResponse> getReservations() {
        ExtractableResponse<Response> ex = given()
            .spec(CommonContext.requestSpec)
            .when().get("admin/reservations")
            .then().statusCode(200)
            .extract();

        return ex.body().jsonPath().getList("", ReservationResponse.class);
    }
}
