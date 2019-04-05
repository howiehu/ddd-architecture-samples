package study.huhao.name.springwithjpa.domain.models.base.excpetions;

public class AggregateException extends RuntimeException {
    public AggregateException(String message) {
        super(message);
    }
}
