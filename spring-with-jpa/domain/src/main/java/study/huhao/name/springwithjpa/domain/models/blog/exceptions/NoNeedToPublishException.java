package study.huhao.name.springwithjpa.domain.models.blog.exceptions;

public class NoNeedToPublishException extends RuntimeException {
    public NoNeedToPublishException() {
        super("no need to publish");
    }
}
