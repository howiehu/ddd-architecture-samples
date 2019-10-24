package study.huhao.demo.interfaces.restapi.resources.publishedblog;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import study.huhao.demo.interfaces.restapi.resources.ResourceTest;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static study.huhao.demo.interfaces.restapi.resources.BasePath.PUBLISHED_BLOG_BASE_PATH;
import static study.huhao.demo.interfaces.restapi.resources.BaseRequestSpecification.createBlogAndGetId;
import static study.huhao.demo.interfaces.restapi.resources.BaseRequestSpecification.publishBlog;
import static study.huhao.demo.interfaces.restapi.resources.BaseResponseSpecification.NOT_FOUND_SPEC;
import static study.huhao.demo.interfaces.restapi.resources.BaseResponseSpecification.OK_SPEC;

@DisplayName(PUBLISHED_BLOG_BASE_PATH + "/{id}")
class PublishedBlogSubResourceTest extends ResourceTest {

    private UUID authorId;

    @BeforeEach
    void setUp() {
        authorId = UUID.randomUUID();
    }

    @Nested
    @DisplayName("GET " + PUBLISHED_BLOG_BASE_PATH + "/{id}")
    class get {

        @Test
        void should_get_blog() {
            var createdBlogId = createBlogAndGetId("Test Blog", "Something...", authorId);

            publishBlog(createdBlogId);

            getPublishedBlogById(createdBlogId)
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
            getPublishedBlogById(blogId)
                    .then()
                    .spec(NOT_FOUND_SPEC)
                    .body("message", is("cannot find the published blog with id " + blogId));
        }


        @Test
        void should_return_404_when_published_blog_not_found() {
            var createdBlogId = createBlogAndGetId("Test Blog", "Something...", authorId);

            getPublishedBlogById(createdBlogId)
                    .then()
                    .spec(NOT_FOUND_SPEC)
                    .body("message", is("cannot find the published blog with id " + createdBlogId));
        }

        private Response getPublishedBlogById(UUID createdBlogId) {
            return given().when().get(buildPath(createdBlogId));
        }
    }

    private String buildPath(UUID blogId) {
        return PUBLISHED_BLOG_BASE_PATH + "/" + blogId;
    }
}
