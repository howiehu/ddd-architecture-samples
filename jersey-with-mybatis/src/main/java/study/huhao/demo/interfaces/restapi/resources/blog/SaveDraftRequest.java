package study.huhao.demo.interfaces.restapi.resources.blog;

import study.huhao.demo.interfaces.restapi.resources.RequestDto;

class SaveDraftRequest implements RequestDto {
    public String title;
    public String body;
}
