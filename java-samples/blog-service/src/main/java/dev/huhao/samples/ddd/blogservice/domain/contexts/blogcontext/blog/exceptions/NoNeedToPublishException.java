package dev.huhao.samples.ddd.blogservice.domain.contexts.blogcontext.blog.exceptions;

import dev.huhao.samples.ddd.blogservice.domain.common.EntityExistedException;

public class NoNeedToPublishException extends EntityExistedException {
    public NoNeedToPublishException() {
        super("the blog has not changed, no need to publish.");
    }
}
