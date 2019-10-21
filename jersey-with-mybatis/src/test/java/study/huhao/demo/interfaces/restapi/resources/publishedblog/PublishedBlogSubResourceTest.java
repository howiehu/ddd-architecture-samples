package study.huhao.demo.interfaces.restapi.resources.publishedblog;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import study.huhao.demo.interfaces.restapi.resources.ResourceTest;
import study.huhao.demo.interfaces.restapi.resources.BaseRequestSpecification;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static study.huhao.demo.interfaces.restapi.resources.BasePath.PUBLISHED_BLOG_BASE_PATH;
import static study.huhao.demo.interfaces.restapi.resources.BaseResponseSpecification.NOT_FOUND_SPEC;
import static study.huhao.demo.interfaces.restapi.resources.BaseResponseSpecification.OK_SPEC;

@DisplayName(PUBLISHED_BLOG_BASE_PATH + "/{id}")
class PublishedBlogSubResourceTest extends ResourceTest {
    @Nested
    @DisplayName("GET " + PUBLISHED_BLOG_BASE_PATH + "/{id}")
    class get {

        @Test
        void should_get_blog() {
            var authorId = UUID.randomUUID();

            var createdBlogId = BaseRequestSpecification.createBlog("Test Blog", "Something...", authorId)
                    .jsonPath()
                    .getUUID("id");

            BaseRequestSpecification.publishBlog(createdBlogId);

            given()
                    .when()
                    .get(buildPath(createdBlogId))
                    .then()
                    .spec(OK_SPEC)
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
                    .when()
                    .get(buildPath(blogId))
                    .then()
                    .spec(NOT_FOUND_SPEC)
                    .body("message", is("cannot find the published blog with id " + blogId));
        }

        @Test
        void should_return_404_when_published_blog_not_found() {
            var authorId = UUID.randomUUID();

            var createdBlogId = BaseRequestSpecification.createBlog("Test Blog", "Something...", authorId)
                    .jsonPath()
                    .getUUID("id");

            given()
                    .when()
                    .get(buildPath(createdBlogId))
                    .then()
                    .spec(NOT_FOUND_SPEC)
                    .body("message", is("cannot find the published blog with id " + createdBlogId));
        }
    }

    private String buildPath(UUID blogId) {
        return PUBLISHED_BLOG_BASE_PATH + "/" + blogId;
    }
}
