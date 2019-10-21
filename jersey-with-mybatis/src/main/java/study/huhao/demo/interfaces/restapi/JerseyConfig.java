package study.huhao.demo.interfaces.restapi;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;
import study.huhao.demo.interfaces.restapi.providers.EntityExistedExceptionMapper;
import study.huhao.demo.interfaces.restapi.providers.EntityNotFoundExceptionMapper;
import study.huhao.demo.interfaces.restapi.providers.NoNeedToPublishExceptionMapper;
import study.huhao.demo.interfaces.restapi.resources.blog.BlogResource;
import study.huhao.demo.interfaces.restapi.resources.publishedblog.PublishedBlogResource;

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
