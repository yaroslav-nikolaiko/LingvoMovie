package lingvo.movie.core.security.utils;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


/**
 * Created by yaroslav on 02.05.15.
 */
@Configuration
public class SecurityUtilsFactory {
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
                if(authentication.getPrincipal() instanceof UserPrincipalWithId)
                    return Voters.voteByUserID((UserPrincipalWithId) authentication.getPrincipal(), object.getRequestUrl());
                return 0;
            }
        };
    }

    Collection<AccessDecisionVoter<? extends FilterInvocation>> defaultVoters() {
        return Arrays.asList(new WebExpressionVoter());
    }
}
