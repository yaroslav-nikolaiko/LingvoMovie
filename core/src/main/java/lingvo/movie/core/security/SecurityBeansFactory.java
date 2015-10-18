package lingvo.movie.core.security;

import lingvo.movie.core.dao.UserRepository;
import lingvo.movie.core.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by yaroslav on 02.05.15.
 */
@Configuration
public class SecurityBeansFactory {
    @Autowired
    UserRepository userRepository;

    @Bean
    public UserDetailsService dataBaseUserDetailsService() {
        return username -> {
            User user = userRepository.findByName(username);
            if(user!=null){
                String ROLE = "admin".equals(user.getName()) ? "ROLE_ADMIN" : "ROLE_USER";
                return new UserPrincipalWithId(
                        user.getId(),
                        user.getName(),
                        user.getPassword(),
                        AuthorityUtils.createAuthorityList(ROLE));
            }else throw new UsernameNotFoundException("could not find the user '" + username + "'");
        };
    }

    @Bean(name = "decisionVoters")
    public List<AccessDecisionVoter<? extends FilterInvocation>> decisionVoters() {
        List<AccessDecisionVoter<? extends FilterInvocation>> decisionVoters = new ArrayList<>();

        decisionVoters.addAll(defaultVoters());
        decisionVoters.add(userIdDecisionVoter());

        return decisionVoters;
    }

    Collection<AccessDecisionVoter<? extends FilterInvocation>> defaultVoters() {
        return Arrays.asList(new WebExpressionVoter());
    }

    AccessDecisionVoter<? extends FilterInvocation> userIdDecisionVoter() {
        return new AccessDecisionVoter<FilterInvocation>() {
            @Override
            public boolean supports(ConfigAttribute attribute) {
                return true;
            }

            @Override
            public boolean supports(Class<?> clazz) {
                return FilterInvocation.class.equals(clazz);
            }

            @Override
            public int vote(Authentication authentication, FilterInvocation object, Collection<ConfigAttribute> attributes) {
                if(authentication.getPrincipal() instanceof UserPrincipalWithId){
                    String url = object.getRequestUrl();
                    UserPrincipalWithId principal = (UserPrincipalWithId) authentication.getPrincipal();

                    String entity;
                    Function<Long, Long> userIdExtractor = null;

                    if (contains(url, entity = "users"))
                        userIdExtractor = id -> id;
                    else if (contains(url, entity = "dictionaries"))
                        userIdExtractor = userRepository::getUserIdByDictionaryId;
                    else if (contains(url, entity = "mediaItems"))
                        userIdExtractor = userRepository::getUserIdByMediaItemId;

                    if(userIdExtractor != null)
                        return vote(extractIdFromUrl(entity, url), principal, userIdExtractor);
                }
                return 0;
            }

            int vote(Long entityId, UserPrincipalWithId principal, Function<Long, Long> userIdExtractor) {
                if (entityId > 0) {
                    Long userId = userIdExtractor.apply(entityId);
                    return principal.getId().equals(userId) ? ACCESS_GRANTED : ACCESS_DENIED;
                } else
                    return 0;
            }

            boolean contains(String url, String entity) {
                return url.contains("/" + entity + "/");
            }

            Long extractIdFromUrl(String entity, String url){
                Pattern pattern = Pattern.compile(".*/"+entity+"/(\\d+).*");
                Matcher matcher = pattern.matcher(url);
                return matcher.find() ? Long.valueOf(matcher.group(1)) : -1L;
            }


        };
    }
}
