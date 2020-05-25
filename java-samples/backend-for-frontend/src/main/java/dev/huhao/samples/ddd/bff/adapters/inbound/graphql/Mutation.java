package dev.huhao.samples.ddd.bff.adapters.inbound.graphql;

import dev.huhao.samples.ddd.bff.adapters.inbound.graphql.resolvers.blog.Draft;
import dev.huhao.samples.ddd.bff.application.services.DraftService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;

@Component
public class Mutation implements GraphQLMutationResolver {

    private final DraftService draftService;

    Mutation(DraftService draftService) {
        this.draftService = draftService;
    }

    Draft createDraft(String title, String body, String authorId) {
        return draftService.createDraft(title, body, authorId);
    }
}
