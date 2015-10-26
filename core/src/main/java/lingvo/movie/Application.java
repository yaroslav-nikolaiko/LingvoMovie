package lingvo.movie;


import lingvo.movie.core.entity.ContentMedia;
import lingvo.movie.core.entity.Dictionary;
import lingvo.movie.core.entity.MediaItem;
import lingvo.movie.core.entity.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

/**
 * Created by yaroslav on 01.03.15.
 */

/*@Configuration
@EnableAutoConfiguration
@ComponentScan*/
@SpringBootApplication
public class Application extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    @Configuration
    public static class RepositoryConfig extends
            RepositoryRestMvcConfiguration {

        @Override
        protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
            config.exposeIdsFor(User.class, Dictionary.class, MediaItem.class, ContentMedia.class);
        }
    }
}


