package study.huhao.demo.infrastructure.persistence.utils;

import org.springframework.data.domain.PageRequest;
import study.huhao.demo.domain.core.Criteria;

public class PaginationUtil {
    public static PageRequest buildPageRequest(Criteria criteria) {
        return PageRequest.of(criteria.getPage() - 1, criteria.getPageSize());
    }
}
