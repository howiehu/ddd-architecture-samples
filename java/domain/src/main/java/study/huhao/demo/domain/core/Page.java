package study.huhao.demo.domain.core;

import lombok.Getter;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public class Page<T> implements ReadModel {
    private List<T> results;
    private int page;
    private int pageSize;
    private long total;

    public Page(List<T> results, int page, int pageSize, long total) {
        this.results = results;
        this.page = page;
        this.pageSize = pageSize;
        this.total = total;
    }

    public <U> Page<U> map(Function<? super T, ? extends U> converter) {
        return new Page<>(getConvertedContent(converter), page, pageSize, total);
    }

    private <U> List<U> getConvertedContent(Function<? super T, ? extends U> converter) {

        if (converter == null) throw new IllegalArgumentException("function must not be null");

        return this.results.stream().map(converter).collect(Collectors.toList());
    }
}
