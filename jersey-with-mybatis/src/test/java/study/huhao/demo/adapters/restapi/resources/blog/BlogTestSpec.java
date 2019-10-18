package study.huhao.demo.adapters.restapi.resources.blog;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static study.huhao.demo.adapters.restapi.resources.BasePath.BLOG_BASE_PATH;

public class BlogTestSpec {

    public static Response createBlog(String title, String body, UUID authorId) {
        return given()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "title", title,
                        "body", body,
                        "authorId", authorId
                ))
                .when()
                .post(BLOG_BASE_PATH);
    }

    static Response getBlog(UUID blogId) {
        return given()
                .when()
                .get(BLOG_BASE_PATH + "/" + blogId);
    }
}
