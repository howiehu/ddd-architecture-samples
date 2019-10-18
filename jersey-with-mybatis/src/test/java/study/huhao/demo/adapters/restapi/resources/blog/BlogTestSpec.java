package study.huhao.demo.adapters.restapi.resources.blog;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;

public class BlogTestSpec {

    static final String BASE_PATH = "/blog";

    public static Response createBlog(String title, String body, UUID authorId) {
        return given()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "title", title,
                        "body", body,
                        "authorId", authorId
                ))
                .when()
                .post(BASE_PATH);
    }

    static Response getBlog(UUID blogId) {
        return given()
                .when()
                .get(BASE_PATH + "/" + blogId);
    }
}
