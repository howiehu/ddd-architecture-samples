package study.huhao.demo.adapters.api.controllers.blog;

import org.flywaydb.test.FlywayTestExecutionListener;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
@DisplayName("/blogs")
class BlogControllerTest {

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

            BlogRE blog = given().port(port)
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
                    .as(BlogRE.class);

            assertThat(blog).isNotNull();
            assertThat(blog.title).isEqualTo("Test Blog");
            assertThat(blog.body).isEqualTo("Something...");
            assertThat(blog.authorId).isEqualTo(authorId);
        }
    }
}
