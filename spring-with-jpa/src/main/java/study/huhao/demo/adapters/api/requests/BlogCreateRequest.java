package study.huhao.demo.adapters.api.requests;

import study.huhao.demo.domain.core.HumbleObject;

public class BlogCreateRequest implements HumbleObject {
    public String title;
    public String body;
    public String authorId;
}
