package lingvo.movie;


import lingvo.movie.core.dao.UserRepository;
import lingvo.movie.core.entity.User;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.ConsensusBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by yaroslav on 01.03.15.
 */
//@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

/*@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {
    @Autowired
    UserRepository userRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
        auth.authenticationProvider(new AbstractUserDetailsAuthenticationProvider() {
            @Override
            protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
                System.out.println(userDetails);
            }

            @Override
            protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
                return userDetailsService().loadUserByUsername(username);
            }
        });
    }



    @Bean
    UserDetailsService userDetailsService() {
        return username -> {
            User user = userRepository.findByName(username);
            if (user!=null) return new org.springframework.security.core.userdetails.User(
                    user.getName(),
                    user.getPassword(),
                    true, true, true, true,
                    AuthorityUtils.createAuthorityList("ROLE_USER"));
            else throw new UsernameNotFoundException("could not find the user '" + username + "'");
        };
    }
}*/

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserRepository userRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/**")    //.authenticated().
                .hasAnyRole("USER", "ADMIN").accessDecisionManager(new AffirmativeBased(decisionVoters()))     //access("authentication.name == 'admin'")
                .and()
                .formLogin();
    }

    private List<AccessDecisionVoter> decisionVoters() {
        AccessDecisionVoter<FilterInvocation> voter = new AccessDecisionVoter<FilterInvocation>() {
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
                String url = object.getRequestUrl();
                if(authentication.getPrincipal() instanceof UserPrincipalWithId) {
                    UserPrincipalWithId user = (UserPrincipalWithId) authentication.getPrincipal();
                    //StringUtils.con
                }
                return 0;
            }
        };
        return Collections.singletonList(voter);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService());
    }

    @Bean
    UserDetailsService customUserDetailsService() {
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

    class UserPrincipalWithId extends org.springframework.security.core.userdetails.User{
        Long id;
        public UserPrincipalWithId(@NotNull Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
            super(username, password, authorities);
            this.id = id;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }

    /*    @Override
    public void configure(WebSecurity web) throws Exception {
        web.
    }*/
}