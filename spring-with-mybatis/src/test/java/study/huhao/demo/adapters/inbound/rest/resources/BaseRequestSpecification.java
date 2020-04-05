package study.huhao.demo.adapters.inbound.rest.resources;

import com.google.common.collect.ImmutableMap;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static study.huhao.demo.adapters.inbound.rest.resources.BasePath.BLOG_BASE_PATH;
import static study.huhao.demo.adapters.inbound.rest.resources.BasePath.PUBLISHED_BLOG_BASE_PATH;

public class BaseRequestSpecification {

    public static Response createBlog(String title, String body, UUID authorId) {
        return given()
                .contentType(ContentType.JSON)
                .body(ImmutableMap.of(
                        "title", title,
                        "body", body,
                        "authorId", authorId
                ))
                .when()
                .post(BLOG_BASE_PATH);
    }

    public static UUID createBlogAndGetId(String title, String body, UUID authorId) {
        return createBlog(title, body, authorId)
                .jsonPath()
                .getUUID("id");
    }

    public static Response getBlog(UUID blogId) {
        return given()
                .when()
                .get(BLOG_BASE_PATH + "/" + blogId);
    }

    public static Response publishBlog(UUID createdBlogId) {
        return given()
                .contentType(JSON)
                .body(ImmutableMap.of("blogId", createdBlogId))
                .when()
                .post(PUBLISHED_BLOG_BASE_PATH);
    }
}
