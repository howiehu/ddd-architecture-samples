package study.huhao.demo.domain.models.blog.exceptions;

import study.huhao.demo.domain.core.excpetions.AggregateException;

public class TitleHasNoContentException extends AggregateException {
    public TitleHasNoContentException() {
        super("the draftTitle cannot be null or no content");
    }
}
