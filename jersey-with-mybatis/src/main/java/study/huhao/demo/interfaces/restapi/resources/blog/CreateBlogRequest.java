package study.huhao.demo.interfaces.restapi.resources.blog;

import study.huhao.demo.interfaces.restapi.resources.RequestDto;

class CreateBlogRequest implements RequestDto {
    public String title;
    public String body;
    public String authorId;
}
