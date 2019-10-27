package study.huhao.demo.adapters.inbound.rest.resources.blog;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import study.huhao.demo.adapters.inbound.rest.resources.BaseRequestSpecification;
import study.huhao.demo.adapters.inbound.rest.resources.ResourceTest;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static study.huhao.demo.adapters.inbound.rest.resources.BasePath.BLOG_BASE_PATH;
import static study.huhao.demo.adapters.inbound.rest.resources.BaseResponseSpecification.CREATED_SPEC;
import static study.huhao.demo.adapters.inbound.rest.resources.BaseResponseSpecification.OK_SPEC;

@DisplayName(BLOG_BASE_PATH)
class BlogResourceTest extends ResourceTest {

    @Nested
    @DisplayName("POST /blog")
    class post {

        @Test
        void should_create_blog() {
            var authorId = UUID.randomUUID();

            BaseRequestSpecification.createBlog("Test Blog", "Something...", authorId)
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
        void should_order_by_createdAt_desc_by_default() {
            createMultiBlog();

            given()
                    .when()
                    .get(BLOG_BASE_PATH + "?limit=5")
                    .then()
                    .spec(OK_SPEC)
                    .body("results", hasSize(5))
                    .body("limit", equalTo(5))
                    .body("offset", equalTo(0))
                    .body("total", equalTo(5))
                    .body("results[0].title", is("Test Blog 1"))
                    .body("results[1].title", is("Test Blog 2"))
                    .body("results[2].title", is("Test Blog 3"))
                    .body("results[3].title", is("Test Blog 4"))
                    .body("results[4].title", is("Test Blog 5"));
        }

        @Test
        void should_get_second_page_blog() {
            createMultiBlog();

            given()
                    .when()
                    .get(BLOG_BASE_PATH + "?limit=3&offset=3")
                    .then()
                    .spec(OK_SPEC)
                    .body("results", hasSize(2))
                    .body("limit", equalTo(3))
                    .body("offset", equalTo(3))
                    .body("total", equalTo(5))
                    .body("results[0].title", is("Test Blog 4"))
                    .body("results[1].title", is("Test Blog 5"));
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

        private void createMultiBlog() {
            for (int i = 0; i < 5; i++) {
                BaseRequestSpecification.createBlog("Test Blog " + (i + 1), "Something...", UUID.randomUUID());
            }
        }

    }
}
