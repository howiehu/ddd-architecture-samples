package study.huhao.demo.adapters.inbound.rest;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;
import study.huhao.demo.adapters.inbound.rest.providers.EntityExistedExceptionMapper;
import study.huhao.demo.adapters.inbound.rest.providers.NoNeedToPublishExceptionMapper;
import study.huhao.demo.adapters.inbound.rest.resources.blog.BlogResource;
import study.huhao.demo.adapters.inbound.rest.resources.publishedblog.PublishedBlogResource;
import study.huhao.demo.adapters.inbound.rest.providers.EntityNotFoundExceptionMapper;

@Component
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(EntityNotFoundExceptionMapper.class);
        register(EntityExistedExceptionMapper.class);
        register(NoNeedToPublishExceptionMapper.class);

        register(BlogResource.class);
        register(PublishedBlogResource.class);
    }
}
