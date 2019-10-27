package study.huhao.demo.domain.core.common.excpetions;

public abstract class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }
}
