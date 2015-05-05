package lingvo.movie.core.entity.utils;

import lingvo.movie.core.entity.lookup.LookupItem;

/**
 * Created by yaroslav on 6/1/14.
 */
public enum Category implements LookupItem {
    KNOWN("Known"),
    LEARNED("Learned"),
    NEW_WORD("New Word");

    String description;
    Category(String description) {
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
