package lingvo.movie.core.entity.lookup;

/**
 * Created by ynikolaiko on 11/25/15.
 */
public enum ContentType implements LookupItem {
    VIDEO("Video"),
    MUSIC("Music"),
    TEXT("Text");

    String description;
    ContentType(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getName() {
        return name();
    }
}
