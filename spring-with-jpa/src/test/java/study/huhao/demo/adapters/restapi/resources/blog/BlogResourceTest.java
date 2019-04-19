package study.huhao.demo.adapters.restapi.resources.blog;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import study.huhao.demo.adapters.restapi.resources.ResourceTest;
import study.huhao.demo.domain.models.blog.Blog;

import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@DisplayName("/blogs")
class BlogResourceTest extends ResourceTest {

    @Nested
    @DisplayName("POST /blogs")
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
    @DisplayName("GET /blogs/{id}")
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
                    .get("/blogs/" + blogId)
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .body("message", equalTo("cannot find the blog with id " + blogId));
        }
    }

    @Nested
    @DisplayName("POST /blogs/{id}/published")
    class publishBlog {

        @Test
        void should_publish_blog() {
            var authorId = UUID.randomUUID().toString();
            var createdBlog = createBlog("Test Blog", "Something...", authorId);

            given()
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .when()
                    .post("/blogs/" + createdBlog.id + "/published")
                    .then()
                    .statusCode(HttpStatus.CREATED.value());

            var publishedBlot = getBlog(createdBlog.id);

            assertThat(publishedBlot).isNotNull();
            assertThat(publishedBlot.id).isEqualTo(createdBlog.id);
            assertThat(publishedBlot.status).isEqualTo(Blog.PublishStatus.Published);
            assertThat(publishedBlot.published).isNotNull();
            assertThat(publishedBlot.published.title).isEqualTo("Test Blog");
            assertThat(publishedBlot.published.body).isEqualTo("Something...");
            assertThat(publishedBlot.published.publishedAt).isNotNull();
        }

        @Test
        void should_return_404_when_blog_not_found() {
            var blogId = UUID.randomUUID().toString();
            given()
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .when()
                    .post("/blogs/" + blogId + "/published")
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .body("message", equalTo("cannot find the blog with id " + blogId));
        }

        @Test
        void should_return_409_when_no_need_to_publish() {
            var authorId = UUID.randomUUID().toString();
            var createdBlog = createBlog("Test Blog", "Something...", authorId);

            given()
                    .when()
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .post("/blogs/" + createdBlog.id + "/published");

            given()
                    .when()
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .post("/blogs/" + createdBlog.id + "/published")
                    .then()
                    .statusCode(HttpStatus.CONFLICT.value())
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .body("message", equalTo("no need to publish"));
        }
    }

    @Nested
    @DisplayName("PUT /blogs/{id}")
    class saveBlog {

        @Test
        void should_save_blog() {
            var authorId = UUID.randomUUID().toString();
            var createdBlog = createBlog("Test Blog", "Something...", authorId);

            given()
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .body(Map.of(
                            "title", "Updated Title",
                            "body", "Updated..."
                    ))
                    .when()
                    .put("/blogs/" + createdBlog.id)
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
        void should_return_404_when_blog_not_found() {
            var blogId = UUID.randomUUID().toString();
            given()
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .body(Map.of(
                            "title", "Updated Title",
                            "body", "Updated..."
                    ))
                    .when()
                    .put("/blogs/" + blogId)
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .body("message", equalTo("cannot find the blog with id " + blogId));
        }
    }

    @Nested
    @DisplayName("DELETE /blogs/{id}")
    class deleteBlog {

        @Test
        void should_delete_blog() {
            var authorId = UUID.randomUUID().toString();
            var createdBlog = createBlog("Test Blog", "Something...", authorId);

            given()
                    .when()
                    .delete("/blogs/" + createdBlog.id)
                    .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());

            given()
                    .when()
                    .get("/blogs/" + createdBlog.id)
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());
        }

        @Test
        void should_return_404_when_blog_not_found() {
            var blogId = UUID.randomUUID().toString();
            given()
                    .when()
                    .delete("/blogs/" + blogId)
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .body("message", equalTo("cannot find the blog with id " + blogId));
        }
    }

    private BlogDto createBlog(String title, String body, String authorId) {
        return given()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(Map.of(
                        "title", title,
                        "body", body,
                        "authorId", authorId
                ))
                .when()
                .post("/blogs")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .extract()
                .as(BlogDto.class);
    }

    private BlogDto getBlog(String blogId) {
        return given()
                .when()
                .get("/blogs/" + blogId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .extract()
                .as(BlogDto.class);
    }
}
