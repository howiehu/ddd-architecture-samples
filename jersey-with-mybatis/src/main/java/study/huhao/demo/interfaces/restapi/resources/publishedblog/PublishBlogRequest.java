package study.huhao.demo.interfaces.restapi.resources.publishedblog;

import study.huhao.demo.interfaces.restapi.resources.RequestDto;

import java.util.UUID;

class PublishBlogRequest implements RequestDto {
    public UUID blogId;
}
