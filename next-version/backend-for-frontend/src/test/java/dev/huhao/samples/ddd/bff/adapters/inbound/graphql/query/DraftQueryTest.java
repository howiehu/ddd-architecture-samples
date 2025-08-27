package dev.huhao.samples.ddd.bff.adapters.inbound.graphql.query;

import org.springframework.graphql.test.tester.WebGraphQlTester;
import dev.huhao.samples.ddd.bff.adapters.inbound.graphql.GraphQLIntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;


class DraftQueryTest extends GraphQLIntegrationTestBase {

    @Autowired
    private WebGraphQlTester graphQlTester;

    @Test
    void draft() {
        
        graphQlTester.document("""
            query {
                draft(blogId: "95c06541-d58c-482e-9cff-5a0f2a696aac") {
                    blogId
                    title
                    body
                    createdAt
                    savedAt
                    author {
                        userId
                        nickname
                    }
                }
            }
            """)
            .execute()
            .path("draft.blogId").entity(String.class).isEqualTo("95c06541-d58c-482e-9cff-5a0f2a696aac")
            .path("draft.title").entity(String.class).isEqualTo("Hello")
            .path("draft.body").entity(String.class).isEqualTo("Hello")
            .path("draft.author.userId").entity(String.class).isEqualTo("3e490af5-085b-48d7-9593-0ceed5fae7af")
            .path("draft.author.nickname").entity(String.class).isEqualTo("Alex");
    }
}

