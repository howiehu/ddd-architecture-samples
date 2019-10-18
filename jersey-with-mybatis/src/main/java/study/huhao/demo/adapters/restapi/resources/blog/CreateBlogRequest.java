package study.huhao.demo.adapters.restapi.resources.blog;

import study.huhao.demo.adapters.restapi.resources.RequestDto;

class CreateBlogRequest implements RequestDto {
    public String title;
    public String body;
    public String authorId;
}
