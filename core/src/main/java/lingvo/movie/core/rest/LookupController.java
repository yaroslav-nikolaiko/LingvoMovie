package lingvo.movie.core.rest;

import lingvo.movie.core.entity.lookup.LookupItem;
import lingvo.movie.core.entity.utils.Category;
import lingvo.movie.core.entity.utils.Language;
import lingvo.movie.core.entity.utils.Level;
import lingvo.movie.core.entity.utils.TextType;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;


/**
 * Created by yaroslav on 06.05.15.
 */
@RestController
@RequestMapping("lookup")
public class LookupController {
    @RequestMapping(value = "{lookupName}", method = RequestMethod.GET)
    public List<LookupItem> getItems(@PathVariable @NotBlank String lookupName) {
        switch (lookupName.toUpperCase()) {
            case "CATEGORY" : return getItems(Category.class);
            case "LANGUAGE" : return getItems(Language.class);
            case "LEVEL" : return getItems(Level.class);
            case "TEXTTYPE" : return getItems(TextType.class);
        }
        return Collections.emptyList();
    }

    List<LookupItem> getItems(Class clazz) {
        return Arrays.asList(clazz.getEnumConstants()).stream()
                .map(item -> LookupItem.clone((LookupItem)item))
                .collect(toList());
    }

    //---------------------------------------------------//
    // ******************** Quick Test *****************//
    //-------------------------------------------------//
    public static void main(String[] args) {
        LookupController controller = new LookupController();
        for (LookupItem category : controller.getItems("Category")) {
            System.out.println("Name " + category.getName());
            System.out.println("Description " + category.getDescription());
        }
    }
}
