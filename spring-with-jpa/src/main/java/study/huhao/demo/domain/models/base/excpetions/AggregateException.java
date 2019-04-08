package study.huhao.demo.domain.models.base.excpetions;

public class AggregateException extends RuntimeException {
    public AggregateException(String message) {
        super(message);
    }
}
