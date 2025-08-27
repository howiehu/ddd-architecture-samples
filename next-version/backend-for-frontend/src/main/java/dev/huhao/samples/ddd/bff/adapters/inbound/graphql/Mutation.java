package dev.huhao.samples.ddd.bff.adapters.inbound.graphql;

import dev.huhao.samples.ddd.bff.adapters.inbound.graphql.resolvers.blog.Draft;
import dev.huhao.samples.ddd.bff.application.services.DraftService;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Controller;

@Controller
public class Mutation {

    private final DraftService draftService;

    Mutation(DraftService draftService) {
        this.draftService = draftService;
    }

    @MutationMapping
    Draft createDraft(@Argument String title, @Argument String body, @Argument String authorId) {
        return draftService.createDraft(title, body, authorId);
    }
}
