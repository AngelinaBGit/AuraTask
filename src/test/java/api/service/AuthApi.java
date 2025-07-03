package api.service;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class AuthApi {
    private static final String LOGIN_URL = "http://localhost:3000/admin/login";

    public static Session loginAndGetSession() {
        Response response = RestAssured.given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("email", "admin@example.com")
                .formParam("password", "password")
                .post(LOGIN_URL)
                .then()
                .statusCode(302)
                .extract().response();

        String cookieHeader = response.getHeader("Set-Cookie");

        String sessionCookie = cookieHeader.split(";", 2)[0];
        return new Session(sessionCookie);
    }
}
