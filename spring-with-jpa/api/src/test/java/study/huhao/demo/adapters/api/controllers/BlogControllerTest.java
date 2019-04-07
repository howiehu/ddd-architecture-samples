package study.huhao.demo.adapters.api.controllers;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.flywaydb.test.FlywayTestExecutionListener;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import study.huhao.demo.application.services.BlogService;

import java.util.Map;
import java.util.UUID;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
@FlywayTest
@Transactional
@DisplayName("/blogs")
class BlogControllerTest {

    @Autowired
    private BlogService blogService;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.standaloneSetup(new BlogController(blogService));
    }

    @Nested
    @DisplayName("POST /blogs")
    class createBlog {

        @Test
        void should_create_blog() {
            given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .body(Map.of(
                            "title", "Test Blog",
                            "body", "Something...",
                            "authorId", UUID.randomUUID().toString()
                    ))
                    .when().post("/blogs")
                    .then()
                    .statusCode(HttpStatus.CREATED.value());
        }
    }
}
