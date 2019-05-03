package study.huhao.demo.adapters.restapi;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;
import study.huhao.demo.adapters.restapi.providers.EntityExistedExceptionMapper;
import study.huhao.demo.adapters.restapi.providers.EntityNotFoundExceptionMapper;
import study.huhao.demo.adapters.restapi.providers.NoNeedToPublishExceptionMapper;
import study.huhao.demo.adapters.restapi.resources.blog.BlogResource;

@Component
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(EntityNotFoundExceptionMapper.class);
        register(EntityExistedExceptionMapper.class);
        register(NoNeedToPublishExceptionMapper.class);

        register(BlogResource.class);
    }
}
