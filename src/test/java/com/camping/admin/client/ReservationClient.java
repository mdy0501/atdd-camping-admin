package com.camping.admin.client;

import com.camping.admin.CommonContext;
import com.camping.admin.dto.CreateReservationRequest;
import com.camping.admin.dto.ReservationResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ReservationClient {
    public ReservationResponse createReservation(CreateReservationRequest createReservationRequest) {
        return given().log().all()
            .spec(CommonContext.requestSpec)
            .when().log().all()
            .body(createReservationRequest)
            .post("admin/reservations")
            .then().log().all()
            .statusCode(201)
            .extract().body().as(ReservationResponse.class);
    }

    public ReservationResponse updateStatus(Long reservationId, String status) {
        return given().log().all()
            .spec(CommonContext.requestSpec)
            .when().log().all()
            .body(Map.of("status", status))
            .patch("admin/reservations/" + reservationId + "/status")
            .then().log().all()
            .statusCode(200)
            .extract().body().as(ReservationResponse.class);
    }

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
