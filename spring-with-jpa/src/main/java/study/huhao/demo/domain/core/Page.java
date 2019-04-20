package study.huhao.demo.domain.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class Page<T> {
    private List<T> results;
    private int limit;
    private int start;
    private int size;
}
