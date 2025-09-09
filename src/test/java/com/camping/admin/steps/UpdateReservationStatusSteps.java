package com.camping.admin.steps;

import com.camping.admin.CommonContext;
import com.camping.admin.client.ReservationClient;
import com.camping.admin.dto.ReservationResponse;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class UpdateReservationStatusSteps {
    private final ReservationClient reservationClient = new ReservationClient();

    @Given("사용자가 예약을 했다")
    public void 사용자가예약을했다() {
        System.out.println("id가 1L인 예약이 생성되었다.");
        CommonContext.reservationId = 1L;
    }

    @When("관리자가 예약 상태를 취소했다")
    public void 관리자가예약상태를취소했다() {
        Response response = given().log().all()
            .contentType("application/json")
            .header("Authorization", "Bearer " + CommonContext.accessToken)
            .when().log().all()
            .body(Map.of("status", "CANCELED"))
            .patch("admin/reservations/" + CommonContext.reservationId + "/status");

        response.then().log().all()
            .statusCode(200);
    }

    @Then("예약 상태는 취소 상태이다")
    public void 예약상태는취소상태이다() {
        ReservationResponse reservation = reservationClient.getReservation(CommonContext.reservationId);
        assertThat(reservation.getStatus()).isEqualTo("CANCELED");
    }


    @And("취소된 예약은 재예약이 가능하다")
    public void 취소된예약은재예약이가능하다() {
        List<ReservationResponse> allReservations = reservationClient.getReservations();
        boolean canRebook = allReservations.stream()
            .anyMatch(r -> r.getId().equals(CommonContext.reservationId) && r.getStatus().equals("CANCELED"));

        assertThat(canRebook).isTrue();
    }
}
