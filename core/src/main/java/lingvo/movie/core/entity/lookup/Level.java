package lingvo.movie.core.entity.lookup;

import lingvo.movie.core.entity.lookup.LookupItem;

/**
 * Created by yaroslav on 6/1/14.
 */
public enum Level implements LookupItem {
    BEGINNER("Beginner"),
    ELEMENTARY("Elementary"),
    INTERMEDIATE("Intermediate"),
    UPPER_INTERMEDIATE("Upper Intermediate"),
    ADVANCED("Advanced"),
    FLUENT("Fluent");

    String description;
    Level(String description) {
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
