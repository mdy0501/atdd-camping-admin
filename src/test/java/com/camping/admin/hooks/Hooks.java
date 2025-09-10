package com.camping.admin.hooks;

import com.camping.admin.CommonContext;
import com.camping.admin.client.AuthClient;
import io.cucumber.java.Before;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;

public class Hooks {
    private final AuthClient authClient = new AuthClient();

    @Before("@관리자가_로그인을_한다")
    public void adminLogin() {
        if (CommonContext.accessToken == null) {
            CommonContext.accessToken = authClient.login("admin", "admin123");

            RestAssured.requestSpecification = new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer " + CommonContext.accessToken)
                .setContentType("application/json")
                .build();
        }
    }
}
