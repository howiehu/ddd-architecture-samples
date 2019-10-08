package study.huhao.demo.domain.models.blog;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import study.huhao.demo.domain.core.Criteria;

@Getter
@SuperBuilder
public class BlogCriteria extends Criteria {

    public BlogCriteria(int limit, long offset) {
        super(limit, offset);
    }
}
