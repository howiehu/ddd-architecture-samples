package study.huhao.name.springwithjpa.domain.models.page;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import study.huhao.name.springwithjpa.domain.models.base.AggregateRoot;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Page implements AggregateRoot {
    @NonNull
    private PageId id;
    @NonNull
    private String title;
    private String body;

    public Page(String title, String body) {
        this.id = new PageId();
        this.title = title;
        this.body = body;
    }
}
