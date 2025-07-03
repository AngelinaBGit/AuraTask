package api.service;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class BaseApiClient {
    public static final String BASE_URI = "http://localhost:3000";
    public static final String LOGIN = "admin@example.com";
    public static final String PASSWORD = "password";
    protected final Session session;

    protected BaseApiClient(Session session) {
        this.session = session;
    }

    protected RequestSpecification givenAuth() {
        return RestAssured.given()
                .baseUri(BASE_URI)
                .headers(session.getAuthHeaders());
    }
}
