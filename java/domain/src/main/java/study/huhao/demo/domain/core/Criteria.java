package study.huhao.demo.domain.core;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public abstract class Criteria {
    private int limit;
    private long offset;

    protected Criteria(int limit, long offset) {
        if (limit < 1) throw new IllegalArgumentException("limit must grater than or equal to 1");
        if (offset < 1) throw new IllegalArgumentException("offset must grater than or equal to 1");

        this.limit = limit;
        this.offset = offset;
    }

    public int getRequestPage() {
        return (int) Math.ceil((double) (getOffset() + 1) / getLimit());
    }
}
