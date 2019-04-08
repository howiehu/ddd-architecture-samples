package study.huhao.demo.domain.core.excpetions;

public class AggregateException extends RuntimeException {
    public AggregateException(String message) {
        super(message);
    }
}
