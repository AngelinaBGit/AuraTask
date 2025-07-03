package api.service;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.models.Post;

import java.util.HashMap;
import java.util.Map;


public class PostApi extends BaseApiClient{
    private static final String CREATE_PATH = "/admin/api/resources/Post/actions/new";
    private static final String EDIT_PATH_TEMPLATE = "/admin/api/resources/Post/records/%d/edit";
    private static final String LIST_PATH = "/admin/api/resources/Post/actions/list";
    public PostApi(Session session) {
        super(session);
    }

    private Response sendPostPayload(String path, Map<String, String> formParams) {
        RequestSpecification request = givenAuth()
                .basePath(path)
                .contentType("application/x-www-form-urlencoded");
        formParams.forEach(request::formParam);
        return request
                .post()
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    private Map<String, String> convertPostToFormParams(Post post) {
        Map<String, String> params = new HashMap<>();
        if (post.getId() != null) params.put("id", String.valueOf(post.getId()));
        if (post.getCreatedAt() != null) params.put("createdAt", post.getCreatedAt());
        if (post.getUpdatedAt() != null) params.put("updatedAt", post.getUpdatedAt());
        if (post.getTitle() != null) params.put("title", post.getTitle());
        if (post.getContent() != null) params.put("content", post.getContent());
        if (post.getStatus() != null) params.put("status", post.getStatus());
        if (post.getPublished() != null) params.put("published", String.valueOf(post.getPublished()));
        if (post.getPublisher() != null) params.put("publisher", String.valueOf(post.getPublisher()));

        if (post.getSomeJson() != null && post.getSomeJson().length > 0) {
            Post.SomeJsonItem item = post.getSomeJson()[0];
            if (item.getNumber() != null) params.put("someJson.0.number", String.valueOf(item.getNumber()));
            if (item.getString() != null) params.put("someJson.0.string", item.getString());
            if (item.getIsBoolean() != null) params.put("someJson.0.boolean", String.valueOf(item.getIsBoolean()));
            if (item.getDate() != null) params.put("someJson.0.date", item.getDate());
        }
        return params;
    }

    public Response createPost(Post post) {
        return sendPostPayload(CREATE_PATH, convertPostToFormParams(post));
    }

    public Response editPost(Post post) {
        if (post.getId() == null) throw new IllegalArgumentException("Post id cannot be null");
        String path = String.format(EDIT_PATH_TEMPLATE, post.getId());
        return sendPostPayload(path, convertPostToFormParams(post));
    }

    public Response getPostList() {
        return givenAuth().basePath(LIST_PATH).get().then().statusCode(200).extract().response();
    }
}

