package study.huhao.demo.adapters.restapi.resources.blog;

import study.huhao.demo.adapters.restapi.resources.RequestDto;

class SaveDraftRequest implements RequestDto {
    public String title;
    public String body;
}
