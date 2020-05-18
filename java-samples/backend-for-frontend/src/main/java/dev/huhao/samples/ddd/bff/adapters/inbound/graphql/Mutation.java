package dev.huhao.samples.ddd.bff.adapters.inbound.graphql;

import dev.huhao.samples.ddd.bff.adapters.inbound.graphql.blog.Draft;
import dev.huhao.samples.ddd.bff.application.service.BlogService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;

@Component
public class Mutation implements GraphQLMutationResolver {

    private final BlogService blogService;

    Mutation(BlogService blogService) {
        this.blogService = blogService;
    }

    Draft createDraft(String title, String body, String authorId) {
        return blogService.createDraft(title, body, authorId);
    }
}
