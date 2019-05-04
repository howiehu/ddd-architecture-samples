package study.huhao.demo.domain.core;

import lombok.Getter;

@Getter
public abstract class Criteria {
    private int page;
    private int pageSize;
    private int limit;
    private int offset;

    protected Criteria(int page, int pageSize) {
        if (page < 1) throw new IllegalArgumentException("page must grater than or equal to 1");
        if (pageSize < 1) throw new IllegalArgumentException("pageSize must grater than or equal to 1");

        this.page = page;
        this.pageSize = pageSize;

        calculateLimitAndOffset();
    }

    private void calculateLimitAndOffset() {
        this.limit = this.pageSize;
        this.offset = (this.page - 1) * this.pageSize;
    }
}
