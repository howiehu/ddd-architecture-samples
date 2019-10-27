package study.huhao.demo.domain.contexts.blogcontext.blog;

import lombok.Getter;
import study.huhao.demo.domain.core.common.Criteria;

@Getter
public class BlogCriteria extends Criteria {

    public BlogCriteria(int limit, long offset) {
        super(limit, offset);
    }
}
