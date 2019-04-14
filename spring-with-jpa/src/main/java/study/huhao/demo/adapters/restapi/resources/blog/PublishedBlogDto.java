package study.huhao.demo.adapters.restapi.resources.blog;

import study.huhao.demo.adapters.restapi.resources.ResponseDto;

import java.time.Instant;

class PublishedBlogDto implements ResponseDto {
    public String title;
    public String body;
    public Instant publishedAt;
}
