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
import static study.huhao.demo.adapters.restapi.resources.BasePath.BLOG_BASE_PATH;
import static study.huhao.demo.adapters.restapi.resources.BaseRequestSpecification.createBlog;
import static study.huhao.demo.adapters.restapi.resources.BaseRequestSpecification.getBlog;
import static study.huhao.demo.adapters.restapi.resources.BaseResponseSpecification.*;

@DisplayName(BLOG_BASE_PATH + "/{id}")
class BlogSubResourceTest extends ResourceTest {

    @Nested
    @DisplayName("GET " + BLOG_BASE_PATH + "/{id}")
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
                    .get(buildPath(blogId))
                    .then()
                    .spec(NOT_FOUND_SPEC)
                    .body("message", is("cannot find the blog with id " + blogId));
        }
    }

    @Nested
    @DisplayName("PUT " + BLOG_BASE_PATH + "/{id}")
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
                    .put(buildPath(createdBlogId))
                    .then()
                    .spec(NO_CONTENT_SPEC);

            Response response = getBlog(createdBlogId);
            response
                    .then()
                    .spec(OK_SPEC)
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
                    .put(buildPath(blogId))
                    .then()
                    .spec(NOT_FOUND_SPEC)
                    .body("message", is("cannot find the blog with id " + blogId));
        }
    }

    @Nested
    @DisplayName("DELETE " + BLOG_BASE_PATH + "/{id}")
    class delete {

        @Test
        void should_delete_blog() {
            var authorId = UUID.randomUUID();
            var createdBlogId = createBlog("Test Blog", "Something...", authorId)
                    .jsonPath()
                    .getUUID("id");

            given()
                    .when()
                    .delete(buildPath(createdBlogId))
                    .then()
                    .spec(NO_CONTENT_SPEC);

            given()
                    .when()
                    .get(buildPath(createdBlogId))
                    .then()
                    .spec(NOT_FOUND_SPEC);
        }

        @Test
        void should_return_404_when_blog_not_found() {
            var blogId = UUID.randomUUID();
            given()
                    .when()
                    .delete(buildPath(blogId))
                    .then()
                    .spec(NOT_FOUND_SPEC)
                    .body("message", is("cannot find the blog with id " + blogId));
        }
    }

    private String buildPath(UUID blogId) {
        return BLOG_BASE_PATH + "/" + blogId;
    }
}
