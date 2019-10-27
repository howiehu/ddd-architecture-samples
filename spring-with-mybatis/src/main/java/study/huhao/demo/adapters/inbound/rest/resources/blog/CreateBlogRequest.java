package study.huhao.demo.adapters.inbound.rest.resources.blog;

import study.huhao.demo.adapters.inbound.rest.resources.RequestDto;

class CreateBlogRequest implements RequestDto {
    public String title;
    public String body;
    public String authorId;
}
