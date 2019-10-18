package study.huhao.demo.adapters.restapi.resources.blog;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import study.huhao.demo.adapters.restapi.resources.ResourceTest;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static study.huhao.demo.adapters.restapi.resources.BaseResponseSpecification.*;
import static study.huhao.demo.adapters.restapi.resources.blog.BlogTestSpec.*;

@DisplayName("/blog/{id}")
class BlogSubResourceTest extends ResourceTest {

    @Nested
    @DisplayName("GET /blog/{id}")
    class get {

        @Test
        void should_get_blog() {
            var authorId = UUID.randomUUID();

            var createdBlogId = createBlog("Test Blog", "Something...", authorId)
                    .jsonPath()
                    .getUUID("id");

            getBlog(createdBlogId)
                    .then()
                    .spec(OK_SPEC)
                    .body(notNullValue())
                    .body("id", is(createdBlogId.toString()))
                    .body("title", is("Test Blog"))
                    .body("body", is("Something..."))
                    .body("authorId", is(authorId.toString()));
        }

        @Test
        void should_return_404_when_blog_not_found() {
            var blogId = UUID.randomUUID();
            given()
                    .when()
                    .get(BASE_PATH + "/" + blogId)
                    .then()
                    .spec(NOT_FOUND_SPEC)
                    .body("message", is("cannot find the blog with id " + blogId));
        }
    }

    @Nested
    @DisplayName("POST /blog/{id}/published")
    class publish {

        @Test
        void should_publish_blog() {
            var authorId = UUID.randomUUID();
            var createdBlogId = createBlog("Test Blog", "Something...", authorId)
                    .jsonPath()
                    .getUUID("id");

            given()
                    .contentType(JSON)
                    .when()
                    .post(BASE_PATH + "/" + createdBlogId + "/published")
                    .then()
                    .spec(NO_CONTENT_SPEC);

            getBlog(createdBlogId)
                    .then()
                    .spec(OK_SPEC)
                    .body(notNullValue())
                    .body("id", is(createdBlogId.toString()))
                    .body("status", is("Published"))
                    .body("published", notNullValue())
                    .body("published.title", is("Test Blog"))
                    .body("published.body", is("Something..."))
                    .body("published.publishedAt", notNullValue());
        }

        @Test
        void should_return_404_when_blog_not_found() {
            var blogId = UUID.randomUUID();
            given()
                    .contentType(JSON)
                    .when()
                    .post(BASE_PATH + "/" + blogId + "/published")
                    .then()
                    .spec(NOT_FOUND_SPEC)
                    .body("message", is("cannot find the blog with id " + blogId));
        }

        @Test
        void should_return_409_when_no_need_to_publish() {
            var authorId = UUID.randomUUID();
            var createdBlogId = createBlog("Test Blog", "Something...", authorId)
                    .jsonPath()
                    .getUUID("id");

            given()
                    .when()
                    .contentType(JSON)
                    .post(BASE_PATH + "/" + createdBlogId + "/published");

            given()
                    .when()
                    .contentType(JSON)
                    .post(BASE_PATH + "/" + createdBlogId + "/published")
                    .then()
                    .spec(CONFLICT_SPEC)
                    .body("message", is("no need to publish"));
        }
    }

    @Nested
    @DisplayName("PUT /blog/{id}")
    class put {

        @Test
        void should_save_blog() {
            var authorId = UUID.randomUUID();
            JsonPath jsonPath = createBlog("Test Blog", "Something...", authorId).jsonPath();
            var createdBlogId = jsonPath.getUUID("id");
            var createdBlogSavedAt = jsonPath.getString("savedAt");

            given()
                    .contentType(JSON)
                    .body(Map.of(
                            "title", "Updated Title",
                            "body", "Updated..."
                    ))
                    .when()
                    .put(BASE_PATH + "/" + createdBlogId)
                    .then()
                    .spec(NO_CONTENT_SPEC);

            Response response = getBlog(createdBlogId);
            response
                    .then()
                    .spec(OK_SPEC)
                    .body(notNullValue())
                    .body("id", is(createdBlogId.toString()))
                    .body("title", is("Updated Title"))
                    .body("body", is("Updated..."));

            String savedAt = response.jsonPath().getString("savedAt");
            assertThat(Instant.parse(savedAt)).isAfter(Instant.parse(createdBlogSavedAt));
        }

        @Test
        void should_return_empty_when_blog_not_found() {
            var blogId = UUID.randomUUID();
            given()
                    .contentType(JSON)
                    .body(Map.of(
                            "title", "Updated Title",
                            "body", "Updated..."
                    ))
                    .when()
                    .put(BASE_PATH + "/" + blogId)
                    .then()
                    .spec(NOT_FOUND_SPEC)
                    .body("message", is("cannot find the blog with id " + blogId));
        }
    }

    @Nested
    @DisplayName("DELETE /blog/{id}")
    class delete {

        @Test
        void should_delete_blog() {
            var authorId = UUID.randomUUID();
            var createdBlogId = createBlog("Test Blog", "Something...", authorId)
                    .jsonPath()
                    .getUUID("id");

            given()
                    .when()
                    .delete(BASE_PATH + "/" + createdBlogId)
                    .then()
                    .spec(NO_CONTENT_SPEC);

            given()
                    .when()
                    .get(BASE_PATH + "/" + createdBlogId)
                    .then()
                    .spec(NOT_FOUND_SPEC);
        }

        @Test
        void should_return_404_when_blog_not_found() {
            var blogId = UUID.randomUUID();
            given()
                    .when()
                    .delete(BASE_PATH + "/" + blogId)
                    .then()
                    .spec(NOT_FOUND_SPEC)
                    .body("message", is("cannot find the blog with id " + blogId));
        }
    }
}
