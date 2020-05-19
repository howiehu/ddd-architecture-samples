package dev.huhao.samples.ddd.bff.adapters.inbound.graphql;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTest;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import dev.huhao.samples.ddd.bff.adapters.inbound.graphql.resolvers.blog.Author;
import dev.huhao.samples.ddd.bff.adapters.inbound.graphql.resolvers.blog.Draft;
import dev.huhao.samples.ddd.bff.application.services.BlogService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@GraphQLTest
class BlogQueryTest {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @MockBean
    private BlogService blogService;

    @Test
    void draft() throws IOException {

        given(blogService.getDraft("95c06541-d58c-482e-9cff-5a0f2a696aac")).willReturn(Draft.builder()
                .blogId("95c06541-d58c-482e-9cff-5a0f2a696aac")
                .title("Hello")
                .body("Hello")
                .createdAt(Instant.now().toString())
                .savedAt(Instant.now().toString())
                .author(Author.builder().userId(UUID.randomUUID().toString()).nickname("Alex").build())
                .build());

        GraphQLResponse response = graphQLTestTemplate.postForResource("queries/draft.graphql");
        assertThat(response).isNotNull();
        assertThat(response.isOk()).isTrue();
        assertThat(response.get("$.data.draft.blogId")).isEqualTo("95c06541-d58c-482e-9cff-5a0f2a696aac");
    }
}
