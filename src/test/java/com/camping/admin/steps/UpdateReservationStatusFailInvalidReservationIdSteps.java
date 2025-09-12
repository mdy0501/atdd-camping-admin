package com.camping.admin.steps;

import com.camping.admin.CommonContext;
import com.camping.admin.client.ReservationClient;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class UpdateReservationStatusFailInvalidReservationIdSteps {
    private final ReservationClient reservationClient = new ReservationClient();

    @When("관리자가 존재하지 않는 예약ID를 사용해서 상태를 변경하려고 한다")
    public void 관리자가존재하지않는예약ID를사용해서상태를변경하려고한다() {
        CommonContext.invalidReservationId = 9999L;
    }

    @Then("관리자가 예약 상태를 변경하면 실패한다")
    public void 관리자가예약상태를변경하면실패한다() {
        assertThatThrownBy(
            () -> reservationClient.updateStatus(CommonContext.invalidReservationId, "CANCELED")
        );
    }
}
