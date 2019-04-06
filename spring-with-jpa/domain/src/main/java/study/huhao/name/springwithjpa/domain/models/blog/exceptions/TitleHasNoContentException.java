package study.huhao.name.springwithjpa.domain.models.blog.exceptions;

import study.huhao.name.springwithjpa.domain.models.base.excpetions.AggregateException;

public class TitleHasNoContentException extends AggregateException {
    public TitleHasNoContentException() {
        super("the draftTitle cannot be null or no content");
    }
}
