package study.huhao.demo.adapters.restapi.resources.blog;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import study.huhao.demo.adapters.restapi.resources.ResourceTest;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsEqual.equalTo;

@DisplayName("/blog")
class BlogResourceTest extends ResourceTest {

    @Nested
    @DisplayName("POST /blog")
    class createBlog {

        @Test
        void should_create_blog() {
            var authorId = UUID.randomUUID();

            createBlog("Test Blog", "Something...", authorId)
                    .then()
                    .body(notNullValue())
                    .statusCode(HttpStatus.CREATED.value())
                    .contentType(ContentType.JSON)
                    .body("title", is("Test Blog"))
                    .body("body", is("Something..."))
                    .body("authorId", is(authorId.toString()));
        }
    }

    @Nested
    @DisplayName("GET /blog/{id}")
    class getBlog {

        @Test
        void should_get_blog() {
            var authorId = UUID.randomUUID();

            var createdBlogId = createBlog("Test Blog", "Something...", authorId)
                    .jsonPath()
                    .getUUID("id");

            getBlog(createdBlogId)
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .contentType(ContentType.JSON)
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
                    .get("/blog/" + blogId)
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .contentType(ContentType.JSON)
                    .body("message", is("cannot find the blog with id " + blogId));
        }
    }

    @Nested
    @DisplayName("POST /blog/{id}/published")
    class publishBlog {

        @Test
        void should_publish_blog() {
            var authorId = UUID.randomUUID();
            var createdBlogId = createBlog("Test Blog", "Something...", authorId)
                    .jsonPath()
                    .getUUID("id");

            given()
                    .contentType(ContentType.JSON)
                    .when()
                    .post("/blog/" + createdBlogId + "/published")
                    .then()
                    .statusCode(HttpStatus.CREATED.value());

            getBlog(createdBlogId)
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .contentType(ContentType.JSON)
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
                    .contentType(ContentType.JSON)
                    .when()
                    .post("/blog/" + blogId + "/published")
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .contentType(ContentType.JSON)
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
                    .contentType(ContentType.JSON)
                    .post("/blog/" + createdBlogId + "/published");

            given()
                    .when()
                    .contentType(ContentType.JSON)
                    .post("/blog/" + createdBlogId + "/published")
                    .then()
                    .statusCode(HttpStatus.CONFLICT.value())
                    .contentType(ContentType.JSON)
                    .body("message", is("no need to publish"));
        }
    }

    @Nested
    @DisplayName("PUT /blog/{id}")
    class saveBlog {

        @Test
        void should_save_blog() {
            var authorId = UUID.randomUUID();
            JsonPath jsonPath = createBlog("Test Blog", "Something...", authorId).jsonPath();
            var createdBlogId = jsonPath.getUUID("id");
            var createdBlogSavedAt = jsonPath.getString("savedAt");

            given()
                    .contentType(ContentType.JSON)
                    .body(Map.of(
                            "title", "Updated Title",
                            "body", "Updated..."
                    ))
                    .when()
                    .put("/blog/" + createdBlogId)
                    .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());

            Response response = getBlog(createdBlogId);
            response
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .contentType(ContentType.JSON)
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
                    .contentType(ContentType.JSON)
                    .body(Map.of(
                            "title", "Updated Title",
                            "body", "Updated..."
                    ))
                    .when()
                    .put("/blog/" + blogId)
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .contentType(ContentType.JSON)
                    .body("message", equalTo("cannot find the blog with id " + blogId));
        }
    }

    @Nested
    @DisplayName("DELETE /blog/{id}")
    class deleteBlog {

        @Test
        void should_delete_blog() {
            var authorId = UUID.randomUUID();
            var createdBlogId = createBlog("Test Blog", "Something...", authorId)
                    .jsonPath()
                    .getUUID("id");

            given()
                    .when()
                    .delete("/blog/" + createdBlogId)
                    .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());

            given()
                    .when()
                    .get("/blog/" + createdBlogId)
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());
        }

        @Test
        void should_return_404_when_blog_not_found() {
            var blogId = UUID.randomUUID();
            given()
                    .when()
                    .delete("/blog/" + blogId)
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .contentType(ContentType.JSON)
                    .body("message", equalTo("cannot find the blog with id " + blogId));
        }
    }

    @Nested
    @DisplayName("GET /blog?limit={limit}&offset={offset}")
    class allBlog {

        @Test
        void should_get_blog_with_pagination() {
            var authorId = UUID.randomUUID();
            createBlog("Test Blog 1", "Something...", authorId);
            createBlog("Test Blog 2", "Something...", authorId);
            createBlog("Test Blog 3", "Something...", authorId);
            createBlog("Test Blog 4", "Something...", authorId);
            createBlog("Test Blog 5", "Something...", authorId);

            given()
                    .when()
                    .get("/blog?limit=3&offset=3")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .contentType(ContentType.JSON)
                    .body("results", hasSize(2))
                    .body("limit", equalTo(3))
                    .body("offset", equalTo(3))
                    .body("total", equalTo(5));
        }

        @Test
        void should_return_empty_results_when_not_found_any_blog() {
            given()
                    .when()
                    .get("/blog?limit=3&offset=4")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .contentType(ContentType.JSON)
                    .body("results", hasSize(0))
                    .body("limit", equalTo(3))
                    .body("offset", equalTo(4))
                    .body("total", equalTo(0));
        }
    }

    private Response createBlog(String title, String body, UUID authorId) {
        return given()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "title", title,
                        "body", body,
                        "authorId", authorId
                ))
                .when()
                .post("/blog");
    }

    private Response getBlog(UUID blogId) {
        return given()
                .when()
                .get("/blog/" + blogId);
    }
}
