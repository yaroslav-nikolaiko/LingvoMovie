package lingvo.movie.core.entity.lookup;

import lombok.Data;

/**
 * Created by yaroslav on 06.05.15.
 */
public interface LookupItem {
    String getName();
    String getDescription();

    static LookupItem clone(LookupItem item){
        return new LookupItem() {

            @Override
            public String getName() {
                return item.getName();
            }

            @Override
            public String getDescription() {
                return item.getDescription();
            }
        };
    }
}
