package api;

import api.service.AuthApi;
import api.service.PostApi;
import api.service.PublisherApi;
import api.service.Session;
import io.restassured.response.Response;
import org.models.Post;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.testng.Assert.assertTrue;

public class CreatePostApiTest {

    @Test
    public void validatePostStatusChangedToRemoved() {
        Session session = AuthApi.loginAndGetSession();
        String uniquePublisherName = "Pub_" + UUID.randomUUID().toString().substring(0, 8);
        String uniquePublisherEmail = "pub_" + UUID.randomUUID().toString().substring(0, 8) + "@mail.com";

        PublisherApi publisherApi = new PublisherApi(session);
        int publisherId = publisherApi.createPublisher(uniquePublisherName, uniquePublisherEmail)
                .jsonPath().getInt("record.id");

        Post.SomeJsonItem someJsonItem = new Post.SomeJsonItem(1, "abc", true, "2025-07-02T22:00:00.000Z");

        Post newPost = Post.builder()
                .title("Post")
                .content("Content")
                .status("ACTIVE")
                .published(true)
                .publisher(publisherId)
                .someJson(new Post.SomeJsonItem[]{someJsonItem})
                .build();

        Response createResponse = new PostApi(session).createPost(newPost);
        int postId = createResponse.jsonPath().getInt("record.id");
        String createdAt = createResponse.jsonPath().getString("record.params.createdAt");

        Post updatedPost = Post.builder()
                .id(postId)
                .createdAt(createdAt)
                .updatedAt(createdAt)
                .title("Post")
                .content("Content")
                .status("REMOVED")
                .published(true)
                .publisher(publisherId)
                .someJson(new Post.SomeJsonItem[]{someJsonItem})
                .build();

        new PostApi(session).editPost(updatedPost);

        Response listResponse = new PostApi(session).getPostList();

        List<Map<String, Object>> records = listResponse.jsonPath().getList("records");

        boolean found = records.stream()
                .anyMatch(r ->
                        r.get("id") instanceof Integer &&
                                (Integer) r.get("id") == postId &&
                                "REMOVED".equals(((Map<?, ?>) r.get("params")).get("status"))
                );

        assertTrue(found, "Post with ID " + postId + " has status REMOVED");
    }
}
