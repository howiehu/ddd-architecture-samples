package study.huhao.demo.domain.models.blog;

import lombok.Getter;
import study.huhao.demo.domain.core.Criteria;

@Getter
public class BlogCriteria extends Criteria {

    public BlogCriteria(int page, int pageSize) {
        super(page, pageSize);
    }
}
