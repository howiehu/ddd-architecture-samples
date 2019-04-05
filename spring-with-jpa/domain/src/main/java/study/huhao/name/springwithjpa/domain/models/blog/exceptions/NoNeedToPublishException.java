package study.huhao.name.springwithjpa.domain.models.blog.exceptions;

import study.huhao.name.springwithjpa.domain.models.base.excpetions.AggregateException;

public class NoNeedToPublishException extends AggregateException {
    public NoNeedToPublishException() {
        super("no need to publish");
    }
}
