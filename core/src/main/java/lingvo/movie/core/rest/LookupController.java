package lingvo.movie.core.rest;

import lingvo.movie.core.entity.lookup.LookupItem;
import lingvo.movie.core.entity.lookup.Category;
import lingvo.movie.core.entity.lookup.Language;
import lingvo.movie.core.entity.lookup.Level;
import lingvo.movie.core.entity.lookup.TextType;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;


/**
 * Created by yaroslav on 06.05.15.
 */
@RestController
@RequestMapping("public/lookup")
@ExposesResourceFor(LookupItem.class)
public class LookupController implements ResourceProcessor<RepositoryLinksResource> {
    @RequestMapping(method = RequestMethod.GET)
    public List<LookupItem> getItems(@RequestParam("name") @NotBlank String lookupName) {
        switch (lookupName.toUpperCase()) {
            case "CATEGORY" : return getItems(Category.class);
            case "LANGUAGE" : return getItems(Language.class);
            case "LEVEL" : return getItems(Level.class);
            case "TEXTTYPE" : return getItems(TextType.class);
        }
        return Collections.emptyList();
    }

    List<LookupItem> getItems(Class<?> clazz) {
        return Arrays.asList((LookupItem[])clazz.getEnumConstants());
    }

    @Override
    public RepositoryLinksResource process(RepositoryLinksResource resource) {
        Link lookup = ControllerLinkBuilder.linkTo(LookupController.class).withRel("lookup");
        resource.add(lookup);
        return resource;
    }
}
