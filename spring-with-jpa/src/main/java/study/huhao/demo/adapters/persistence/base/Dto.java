package study.huhao.demo.adapters.persistence.base;

public interface Dto<T> extends HumbleObject {
    T toDomainModel();
}
