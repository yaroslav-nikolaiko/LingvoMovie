package lingvo.movie.core.rest;

import lingvo.movie.core.entity.lookup.LookupItem;
import lingvo.movie.core.entity.utils.Category;
import lingvo.movie.core.entity.utils.Language;
import lingvo.movie.core.entity.utils.Level;
import lingvo.movie.core.entity.utils.TextType;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

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
@ExposesResourceFor(LookupItem.class)
public class LookupController implements ResourceProcessor<RepositoryLinksResource> {
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

/*    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<Resources<LookupItem>> getItems(@RequestParam("name") @NotBlank String lookupName) {
        switch (lookupName.toUpperCase()) {
            case "CATEGORY" : return getItems(Category.class);
            case "LANGUAGE" : return getItems(Language.class);
            case "LEVEL" : return getItems(Level.class);
            case "TEXTTYPE" : return getItems(TextType.class);
        }
        return new HttpEntity<>(new Resources<>(Collections.emptyList()));
    }*/

    List<LookupItem> getItems(Class clazz) {
        return Arrays.asList(clazz.getEnumConstants()).stream()
                .map(item -> LookupItem.clone((LookupItem)item))
                .collect(toList());
    }

/*    HttpEntity<Resources<LookupItem>> getItems(Class clazz) {
        return new HttpEntity<>( new Resources<>(Arrays.asList(clazz.getEnumConstants()).stream()
                .map(item -> LookupItem.clone((LookupItem)item))
                .collect(toList())));
    }*/

    @Override
    public RepositoryLinksResource process(RepositoryLinksResource resource) {
        Link lookup = ControllerLinkBuilder.linkTo(LookupController.class).withRel("lookup");
        resource.add(lookup);
        return resource;
    }
}
