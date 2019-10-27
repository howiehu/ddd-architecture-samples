package study.huhao.demo.adapters.inbound.rest.resources.publishedblog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import study.huhao.demo.adapters.inbound.rest.resources.BasePath;
import study.huhao.demo.adapters.inbound.rest.resources.BaseRequestSpecification;
import study.huhao.demo.adapters.inbound.rest.resources.BaseResponseSpecification;
import study.huhao.demo.adapters.inbound.rest.resources.ResourceTest;

import java.util.UUID;

import static org.hamcrest.Matchers.*;

@DisplayName(BasePath.PUBLISHED_BLOG_BASE_PATH)
class PublishedBlogResourceTest extends ResourceTest {

    private UUID authorId;

    @BeforeEach
    void setUp() {
        authorId = UUID.randomUUID();
    }

    @Nested
    @DisplayName("POST /published-blog")
    class post {

        @Test
        void should_publish_blog() {
            var createdBlogId = BaseRequestSpecification.createBlogAndGetId("Test Blog", "Something...", authorId);

            BaseRequestSpecification.publishBlog(createdBlogId)
                    .then()
                    .spec(BaseResponseSpecification.CREATED_SPEC)
                    .header("Location", containsString(BasePath.PUBLISHED_BLOG_BASE_PATH + "/" + createdBlogId))
                    .body("id", is(createdBlogId.toString()))
                    .body("title", is("Test Blog"))
                    .body("body", is("Something..."))
                    .body("authorId", is(authorId.toString()))
                    .body("publishedAt", notNullValue());
        }

        @Test
        void should_return_404_when_blog_not_found() {
            var blogId = UUID.randomUUID();
            BaseRequestSpecification.publishBlog(blogId)
                    .then()
                    .spec(BaseResponseSpecification.NOT_FOUND_SPEC)
                    .body("message", is("cannot find the blog with id " + blogId));
        }

        @Test
        void should_return_409_when_no_need_to_publish() {
            var createdBlogId = BaseRequestSpecification.createBlogAndGetId("Test Blog", "Something...", authorId);

            BaseRequestSpecification.publishBlog(createdBlogId);

            BaseRequestSpecification.publishBlog(createdBlogId)
                    .then()
                    .spec(BaseResponseSpecification.CONFLICT_SPEC)
                    .body("message", is("no need to publish"));
        }
    }

}
