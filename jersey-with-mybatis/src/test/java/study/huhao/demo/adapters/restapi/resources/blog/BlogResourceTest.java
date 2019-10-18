package study.huhao.demo.adapters.restapi.resources.blog;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import study.huhao.demo.adapters.restapi.resources.ResourceTest;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static study.huhao.demo.adapters.restapi.resources.BasePath.BLOG_BASE_PATH;
import static study.huhao.demo.adapters.restapi.resources.BaseResponseSpecification.CREATED_SPEC;
import static study.huhao.demo.adapters.restapi.resources.BaseResponseSpecification.OK_SPEC;
import static study.huhao.demo.adapters.restapi.resources.BaseRequestSpecification.createBlog;

@DisplayName(BLOG_BASE_PATH)
class BlogResourceTest extends ResourceTest {

    @Nested
    @DisplayName("POST /blog")
    class post {

        @Test
        void should_create_blog() {
            var authorId = UUID.randomUUID();

            createBlog("Test Blog", "Something...", authorId)
                    .then()
                    .spec(CREATED_SPEC)
                    .body("id", notNullValue())
                    .body("title", is("Test Blog"))
                    .body("body", is("Something..."))
                    .body("authorId", is(authorId.toString()))
                    .header("Location", response -> containsString(BLOG_BASE_PATH + "/" + response.path("id")));
        }
    }

    @Nested
    @DisplayName("GET " + BLOG_BASE_PATH + "?limit={limit}&offset={offset}")
    class get {

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
                    .get(BLOG_BASE_PATH + "?limit=3&offset=3")
                    .then()
                    .spec(OK_SPEC)
                    .body("results", hasSize(2))
                    .body("limit", equalTo(3))
                    .body("offset", equalTo(3))
                    .body("total", equalTo(5));
        }

        @Test
        void should_return_empty_results_when_not_found_any_blog() {
            given()
                    .when()
                    .get(BLOG_BASE_PATH + "?limit=3&offset=4")
                    .then()
                    .spec(OK_SPEC)
                    .body("results", hasSize(0))
                    .body("limit", equalTo(3))
                    .body("offset", equalTo(4))
                    .body("total", equalTo(0));
        }
    }

}
