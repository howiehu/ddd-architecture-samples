package study.huhao.demo.adapters.restapi.resources.publishedblog;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import study.huhao.demo.adapters.restapi.resources.ResourceTest;

import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static study.huhao.demo.adapters.restapi.resources.BasePath.PUBLISHED_BLOG_BASE_PATH;
import static study.huhao.demo.adapters.restapi.resources.BaseResponseSpecification.*;
import static study.huhao.demo.adapters.restapi.resources.BaseRequestSpecification.createBlog;

@DisplayName(PUBLISHED_BLOG_BASE_PATH)
class PublishedBlogResourceTest extends ResourceTest {

    @Nested
    @DisplayName("POST /published-blog")
    class post {

        @Test
        void should_publish_blog() {
            var authorId = UUID.randomUUID();

            var createdBlogId = createBlog("Test Blog", "Something...", authorId)
                    .jsonPath()
                    .getUUID("id");

            given()
                    .contentType(JSON)
                    .body(Map.of("blogId", createdBlogId))
                    .when()
                    .post(PUBLISHED_BLOG_BASE_PATH)
                    .then()
                    .spec(CREATED_SPEC)
                    .header("Location", containsString(PUBLISHED_BLOG_BASE_PATH + "/" + createdBlogId))
                    .body("id", is(createdBlogId.toString()))
                    .body("title", is("Test Blog"))
                    .body("body", is("Something..."))
                    .body("authorId", is(authorId.toString()))
                    .body("publishedAt", notNullValue());
        }

        @Test
        void should_return_404_when_blog_not_found() {
            var blogId = UUID.randomUUID();
            given()
                    .contentType(JSON)
                    .body(Map.of("blogId", blogId))
                    .when()
                    .post(PUBLISHED_BLOG_BASE_PATH)
                    .then()
                    .spec(NOT_FOUND_SPEC)
                    .body("message", is("cannot find the blog with id " + blogId));
        }

        @Test
        void should_return_409_when_no_need_to_publish() {
            var authorId = UUID.randomUUID();
            var createdBlogId = createBlog("Test Blog", "Something...", authorId)
                    .jsonPath()
                    .getUUID("id");

            given()
                    .contentType(JSON)
                    .body(Map.of("blogId", createdBlogId))
                    .when()
                    .post(PUBLISHED_BLOG_BASE_PATH);

            given()
                    .contentType(JSON)
                    .body(Map.of("blogId", createdBlogId))
                    .when()
                    .post(PUBLISHED_BLOG_BASE_PATH)
                    .then()
                    .spec(CONFLICT_SPEC)
                    .body("message", is("no need to publish"));
        }
    }
}
