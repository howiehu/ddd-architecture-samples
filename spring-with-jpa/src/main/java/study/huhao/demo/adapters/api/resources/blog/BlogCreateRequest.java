package study.huhao.demo.adapters.api.resources.blog;

import study.huhao.demo.adapters.api.resources.RequestDto;

class BlogCreateRequest implements RequestDto {
    public String title;
    public String body;
    public String authorId;
}
