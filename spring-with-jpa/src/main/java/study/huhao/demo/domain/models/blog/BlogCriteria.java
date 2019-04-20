package study.huhao.demo.domain.models.blog;

import lombok.Builder;
import lombok.Data;
import study.huhao.demo.domain.core.Criteria;

@Data
@Builder
public class BlogCriteria implements Criteria {
    private int limit;
    private int offset;
}
