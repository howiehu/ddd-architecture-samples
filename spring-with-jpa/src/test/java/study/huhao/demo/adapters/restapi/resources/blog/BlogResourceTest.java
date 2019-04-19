package study.huhao.demo.adapters.restapi.resources.blog;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import study.huhao.demo.adapters.restapi.resources.ResourceTest;

import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@DisplayName("/blogs")
class BlogResourceTest extends ResourceTest {

    @LocalServerPort
    private int port;

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
            given().port(port)
                    .when()
                    .get("/blogs/" + blogId)
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .body("message", equalTo("cannot find the blog with id " + blogId));
        }
    }

    @Nested
    @DisplayName("PUT /blogs/{id}")
    class saveBlog {

        @Test
        void should_save_blog() {
            var authorId = UUID.randomUUID().toString();
            var createdBlog = createBlog("Test Blog", "Something...", authorId);

            given().port(port)
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
            assertThat(updatedBlog.authorId).isEqualTo(createdBlog.authorId);
            assertThat(updatedBlog.createdAt).isEqualTo(createdBlog.createdAt);
            assertThat(updatedBlog.status).isEqualTo(createdBlog.status);
            assertThat(updatedBlog.savedAt).isAfter(createdBlog.savedAt);
        }

        @Test
        void should_return_404_when_blog_not_found() {
            var blogId = UUID.randomUUID().toString();
            given().port(port)
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

    private BlogDto createBlog(String title, String body, String authorId) {
        return given().port(port)
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
        return given().port(port)
                .when()
                .get("/blogs/" + blogId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .extract()
                .as(BlogDto.class);
    }
}
