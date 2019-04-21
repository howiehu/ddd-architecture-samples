package study.huhao.demo.adapters.restapi.resources.blog;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import study.huhao.demo.adapters.restapi.resources.ResourceTest;
import study.huhao.demo.domain.models.blog.Blog;

import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;

@DisplayName("/blog")
class BlogResourceTest extends ResourceTest {

    @Nested
    @DisplayName("POST /blog")
    class createBlog {

        @Test
        void should_create_blog() {
            var authorId = UUID.randomUUID().toString();

            var blog = createBlog("Test Blog", "Something...", authorId);

            assertThat(blog).isNotNull();
            assertThat(blog.title).isEqualTo("Test Blog");
            assertThat(blog.body).isEqualTo("Something...");
            assertThat(blog.authorId).isEqualTo(authorId);
        }
    }

    @Nested
    @DisplayName("GET /blog/{id}")
    class getBlog {

        @Test
        void should_get_blog() {
            var authorId = UUID.randomUUID().toString();
            var createdBlog = createBlog("Test Blog", "Something...", authorId);

            var blog = getBlog(createdBlog.id);

            assertThat(blog).isNotNull();
            assertThat(blog.id).isEqualTo(createdBlog.id);
            assertThat(blog.title).isEqualTo("Test Blog");
            assertThat(blog.body).isEqualTo("Something...");
            assertThat(blog.authorId).isEqualTo(authorId);
        }

        @Test
        void should_return_404_when_blog_not_found() {
            var blogId = UUID.randomUUID().toString();
            given()
                    .when()
                    .get("/blog/" + blogId)
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .contentType(ContentType.JSON)
                    .body("message", equalTo("cannot find the blog with id " + blogId));
        }
    }

    @Nested
    @DisplayName("POST /blog/{id}/published")
    class publishBlog {

        @Test
        void should_publish_blog() {
            var authorId = UUID.randomUUID().toString();
            var createdBlog = createBlog("Test Blog", "Something...", authorId);

            given()
                    .contentType(ContentType.JSON)
                    .when()
                    .post("/blog/" + createdBlog.id + "/published")
                    .then()
                    .statusCode(HttpStatus.CREATED.value());

            var publishedBlot = getBlog(createdBlog.id);

            assertThat(publishedBlot).isNotNull();
            assertThat(publishedBlot.id).isEqualTo(createdBlog.id);
            assertThat(publishedBlot.status).isEqualTo(Blog.Status.Published);
            assertThat(publishedBlot.published).isNotNull();
            assertThat(publishedBlot.published.title).isEqualTo("Test Blog");
            assertThat(publishedBlot.published.body).isEqualTo("Something...");
            assertThat(publishedBlot.published.publishedAt).isNotNull();
        }

        @Test
        void should_return_404_when_blog_not_found() {
            var blogId = UUID.randomUUID().toString();
            given()
                    .contentType(ContentType.JSON)
                    .when()
                    .post("/blog/" + blogId + "/published")
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .contentType(ContentType.JSON)
                    .body("message", equalTo("cannot find the blog with id " + blogId));
        }

        @Test
        void should_return_409_when_no_need_to_publish() {
            var authorId = UUID.randomUUID().toString();
            var createdBlog = createBlog("Test Blog", "Something...", authorId);

            given()
                    .when()
                    .contentType(ContentType.JSON)
                    .post("/blog/" + createdBlog.id + "/published");

            given()
                    .when()
                    .contentType(ContentType.JSON)
                    .post("/blog/" + createdBlog.id + "/published")
                    .then()
                    .statusCode(HttpStatus.CONFLICT.value())
                    .contentType(ContentType.JSON)
                    .body("message", equalTo("no need to publish"));
        }
    }

    @Nested
    @DisplayName("PUT /blog/{id}")
    class saveBlog {

        @Test
        void should_save_blog() {
            var authorId = UUID.randomUUID().toString();
            var createdBlog = createBlog("Test Blog", "Something...", authorId);

            given()
                    .contentType(ContentType.JSON)
                    .body(Map.of(
                            "title", "Updated Title",
                            "body", "Updated..."
                    ))
                    .when()
                    .put("/blog/" + createdBlog.id)
                    .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());

            var updatedBlog = getBlog(createdBlog.id);

            assertThat(updatedBlog).isNotNull();
            assertThat(updatedBlog.id).isEqualTo(createdBlog.id);
            assertThat(updatedBlog.title).isEqualTo("Updated Title");
            assertThat(updatedBlog.body).isEqualTo("Updated...");
            assertThat(updatedBlog.savedAt).isAfter(createdBlog.savedAt);
        }

        @Test
        void should_return_empty_when_blog_not_found() {
            var blogId = UUID.randomUUID().toString();
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
            var authorId = UUID.randomUUID().toString();
            var createdBlog = createBlog("Test Blog", "Something...", authorId);

            given()
                    .when()
                    .delete("/blog/" + createdBlog.id)
                    .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());

            given()
                    .when()
                    .get("/blog/" + createdBlog.id)
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());
        }

        @Test
        void should_return_404_when_blog_not_found() {
            var blogId = UUID.randomUUID().toString();
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
            var authorId = UUID.randomUUID().toString();
            createBlog("Test Blog 1", "Something...", authorId);
            createBlog("Test Blog 2", "Something...", authorId);
            createBlog("Test Blog 3", "Something...", authorId);
            createBlog("Test Blog 5", "Something...", authorId);
            createBlog("Test Blog 6", "Something...", authorId);

            given()
                    .when()
                    .get("/blog?limit=3&offset=4")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .contentType(ContentType.JSON)
                    .body("results", hasSize(2))
                    .body("limit", equalTo(3))
                    .body("offset", equalTo(4))
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

    private BlogDto createBlog(String title, String body, String authorId) {
        return given()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "title", title,
                        "body", body,
                        "authorId", authorId
                ))
                .when()
                .post("/blog")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(BlogDto.class);
    }

    private BlogDto getBlog(String blogId) {
        return given()
                .when()
                .get("/blog/" + blogId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(BlogDto.class);
    }
}
