package dev.huhao.samples.ddd.bff.adapters.inbound.graphql.mutation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import dev.huhao.samples.ddd.bff.adapters.inbound.graphql.GraphQLIntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.util.ResourceUtils.CLASSPATH_URL_PREFIX;
import static org.springframework.util.ResourceUtils.getFile;

public class DraftMutationTest extends GraphQLIntegrationTestBase {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void createDraft() throws IOException {
        GraphQLResponse response = graphQLTestTemplate.postForResource("fixture/mutation/createDraft.graphql");

        assertThat(response).isNotNull();
        assertThat(response.isOk()).isTrue();

        JsonNode expectedResult = mapper
                .readTree(getFile(CLASSPATH_URL_PREFIX + "fixture/mutation/results/create-draft-result.json"));
        assertThat(response.readTree()).isEqualTo(expectedResult);
    }
}
