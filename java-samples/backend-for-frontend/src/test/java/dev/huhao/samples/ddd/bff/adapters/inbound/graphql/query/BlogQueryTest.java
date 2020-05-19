package dev.huhao.samples.ddd.bff.adapters.inbound.graphql.query;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import dev.huhao.samples.ddd.bff.adapters.inbound.graphql.GraphQLIntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class BlogQueryTest extends GraphQLIntegrationTestBase {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @Test
    void draft() throws IOException {

        GraphQLResponse response = graphQLTestTemplate.postForResource("queries/draft.graphql");

        assertThat(response).isNotNull();
        assertThat(response.isOk()).isTrue();
        assertThat(response.get("$.data.draft.blogId")).isEqualTo("95c06541-d58c-482e-9cff-5a0f2a696aac");
        assertThat(response.get("$.data.draft.title")).isEqualTo("Hello");
        assertThat(response.get("$.data.draft.body")).isEqualTo("Hello");
        assertThat(response.get("$.data.draft.createdAt")).isEqualTo("2020-05-18T13:57:09.635Z");
        assertThat(response.get("$.data.draft.savedAt")).isEqualTo("2020-05-18T13:57:09.635Z");
        assertThat(response.get("$.data.draft.author.userId")).isEqualTo("95c06541-d58c-482e-9cff-5a0f2a696aac");
        assertThat(response.get("$.data.draft.author.nickname")).isEqualTo("Alex");
    }
}

