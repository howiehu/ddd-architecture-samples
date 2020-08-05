package study.huhao.demo.domain.core.common;

public abstract class Criteria {
    private final int limit;
    private final long offset;

    public int getLimit() {
        return this.limit;
    }

    public long getOffset() {
        return this.offset;
    }

    protected Criteria(int limit, long offset) {
        if (limit < 1) throw new IllegalArgumentException("limit must grater than or equal to 1");
        if (offset < 0) throw new IllegalArgumentException("offset must grater than or equal to 0");

        this.limit = limit;
        this.offset = offset;
    }

    public int getRequestPage() {
        return (int) Math.ceil((double) (getOffset() + 1) / getLimit());
    }
}
