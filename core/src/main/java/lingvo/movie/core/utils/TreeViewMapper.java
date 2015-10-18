package lingvo.movie.core.utils;

import lingvo.movie.core.entity.MediaItem;
import lingvo.movie.core.entity.dto.MediaItemsTreeView;
import org.apache.commons.io.FilenameUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by yaroslav on 10/17/15.
 */
public class TreeViewMapper {
    public static List<MediaItemsTreeView> toTreeView(List<MediaItem> items) {
        List<MediaItemsTreeView> tree = new ArrayList<>();
        items.forEach(item->insertItem(item,tree));
        return tree;
    }

    static void insertItem(MediaItem item, List<MediaItemsTreeView> tree) {
        String path = item.getDisplayPath();
        MediaItemsTreeView lastFolder = insertFolders(getFirstFolder(path), path, tree);
        lastFolder.addNode(new MediaItemsTreeView(item));
    }

    static MediaItemsTreeView insertFolders(String currentPath, String fullPath, List<MediaItemsTreeView> tree) {
        currentPath = normalize(currentPath);
        fullPath = normalize(fullPath);

        String title = FilenameUtils.getBaseName(currentPath);
        Optional<MediaItemsTreeView> currentNodeOptional = tree.stream()
                .filter(node -> node.getTitle().equals(title))
                .findFirst();
        MediaItemsTreeView currentNode;
        if (!currentNodeOptional.isPresent()) {
            currentNode = new MediaItemsTreeView(title, currentPath);
            tree.add(currentNode);
        } else
            currentNode = currentNodeOptional.get();
        if (!currentPath.equals(fullPath))
            return insertFolders(getNextFolder(currentPath, fullPath), fullPath, currentNode.getNodes());
        else
            return currentNode;
    }

    static String getNextFolder(String currentPath, String fullPath){
        String restOfThePath = fullPath.substring(currentPath.length());
        String nextFolder = getFirstFolder(restOfThePath);
        return FilenameUtils.concat(currentPath, nextFolder);
    }

    static String getFirstFolder(String path){
        if("/".equals(String.valueOf(path.charAt(0))))
            path = path.substring(1);
        return path.indexOf("/") > 0 ? path.substring(0, path.indexOf("/")) : path;
    }

    static String normalize(String path) {
        path = FilenameUtils.normalizeNoEndSeparator(path);
        return path.startsWith("/") ? path.substring(1) : path;
    }
}
