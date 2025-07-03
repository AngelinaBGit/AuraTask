package api.service;

import io.restassured.response.Response;

public class PublisherApi extends BaseApiClient{
    private static final String CREATE_PATH = "/admin/api/resources/Publisher/actions/new";
    public PublisherApi(Session session) {
        super(session);
    }

    public Response createPublisher(String name, String email) {
        return givenAuth()
                .basePath(CREATE_PATH)
                .log().all()
                .contentType("application/x-www-form-urlencoded")
                .formParam("name", name)
                .formParam("email", email)
                .post()
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();
    }
}
