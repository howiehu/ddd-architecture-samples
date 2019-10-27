package study.huhao.demo.adapters.inbound.rest.resources.blog;

import study.huhao.demo.adapters.inbound.rest.resources.RequestDto;

class SaveDraftRequest implements RequestDto {
    public String title;
    public String body;
}
