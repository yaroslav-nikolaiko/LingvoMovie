package lingvo.movie.core.entity.dto;

import lingvo.movie.core.entity.MediaItem;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static lingvo.movie.core.utils.TreeViewMapper.toTreeView;

/**
 * Created by yaroslav on 10/17/15.
 */
@Data
public class MediaItemsTreeView {
    @NotNull String title;
    @NotNull String displayPath;
    MediaItem item;
    List<MediaItemsTreeView> nodes = new ArrayList<>();

    public static List<MediaItemsTreeView> createTreeView(List<MediaItem> items) {
        return toTreeView(items);
    }

    public MediaItemsTreeView(@NotNull MediaItem item) {
        this.item = item;
        this.title = item.getName();
        this.displayPath = item.getDisplayPath();
    }

    public MediaItemsTreeView(@NotNull String title, @NotNull String displayPath) {
        this.title = title;
        this.displayPath = displayPath;
    }

    public void addNode(@NotNull MediaItemsTreeView nodeAngular) {
        nodes.add(nodeAngular);
    }
}
