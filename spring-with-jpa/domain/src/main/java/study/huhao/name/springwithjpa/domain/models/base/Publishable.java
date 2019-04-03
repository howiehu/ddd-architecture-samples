package study.huhao.name.springwithjpa.domain.models.base;

public interface Publishable {
    void publish();

    enum PublishStatus {
        Draft,
        Published,
        Hiddened
    }
}
