package study.huhao.demo.adapters.api.requests;

import lombok.Getter;

@Getter
public class BlogCreateRequest {
    private String title;
    private String body;
    private String authorId;
}
