package lingvo.movie.core.entity.lookup;

import lingvo.movie.core.entity.lookup.LookupItem;

/**
 * Created by yaroslav on 01.03.15.
 */
public enum TextType implements LookupItem {
    SUBTITLES("Subtitles"),
    LYRICS("Lyrics"),
    TEXT("Text");

    String description;
    TextType(String description) {
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
