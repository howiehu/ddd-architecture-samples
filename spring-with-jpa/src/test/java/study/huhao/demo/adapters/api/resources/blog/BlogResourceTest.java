package study.huhao.demo.adapters.api.resources.blog;

import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import study.huhao.demo.adapters.api.resources.ResourceTest;

import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("/blogs")
class BlogResourceTest extends ResourceTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    @FlywayTest
    void setUp() {
    }

    @Nested
    @DisplayName("POST /blogs")
    class createBlog {

        @Test
        void should_create_blog() {
            String authorId = UUID.randomUUID().toString();

            BlogDto blog = given().port(port)
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .body(Map.of(
                            "title", "Test Blog",
                            "body", "Something...",
                            "authorId", authorId
                    ))
                    .when().post("/blogs")
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .extract()
                    .as(BlogDto.class);

            assertThat(blog).isNotNull();
            assertThat(blog.title).isEqualTo("Test Blog");
            assertThat(blog.body).isEqualTo("Something...");
            assertThat(blog.authorId).isEqualTo(authorId);
        }
    }
}
