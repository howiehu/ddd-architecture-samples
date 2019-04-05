package study.huhao.name.springwithjpa.domain.models.blog.exceptions;

public class TitleHasNoContentException extends RuntimeException {
    public TitleHasNoContentException() {
        super("the title cannot be null or no content");
    }
}
