package study.huhao.demo.adapters.api.requests;

import lombok.Getter;
import study.huhao.demo.domain.core.HumbleObject;

@Getter
public class BlogCreateRequest implements HumbleObject {
    private String title;
    private String body;
    private String authorId;
}
