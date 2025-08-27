package dev.huhao.samples.ddd.bff.adapters.inbound.graphql.mutation;

import org.springframework.graphql.test.tester.WebGraphQlTester;
import dev.huhao.samples.ddd.bff.adapters.inbound.graphql.GraphQLIntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;


public class DraftMutationTest extends GraphQLIntegrationTestBase {

    @Autowired
    private WebGraphQlTester graphQlTester;

    @Test
    void createDraft() {
        
        graphQlTester.document("""
            mutation {
                createDraft(
                    title: "Hello"
                    body: "Hello"
                    authorId: "3e490af5-085b-48d7-9593-0ceed5fae7af"
                ) {
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
            .path("createDraft.blogId").entity(String.class).isEqualTo("95c06541-d58c-482e-9cff-5a0f2a696aac")
            .path("createDraft.title").entity(String.class).isEqualTo("Hello")
            .path("createDraft.body").entity(String.class).isEqualTo("Hello")
            .path("createDraft.author.userId").entity(String.class).isEqualTo("3e490af5-085b-48d7-9593-0ceed5fae7af")
            .path("createDraft.author.nickname").entity(String.class).isEqualTo("Alex");
    }
}
