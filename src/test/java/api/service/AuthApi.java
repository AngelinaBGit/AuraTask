package api.service;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static api.service.BaseApiClient.LOGIN;
import static api.service.BaseApiClient.PASSWORD;

public class AuthApi {
    private static final String LOGIN_URL = "http://localhost:3000/admin/login";

    public static Session loginAndGetSession() {
        Response response = RestAssured.given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("email", LOGIN)
                .formParam("password", PASSWORD)
                .post(LOGIN_URL)
                .then()
                .statusCode(302)
                .extract().response();

        String cookieHeader = response.getHeader("Set-Cookie");

        String sessionCookie = cookieHeader.split(";", 2)[0];
        return new Session(sessionCookie);
    }
}
