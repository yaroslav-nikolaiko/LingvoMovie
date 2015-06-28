package lingvo.movie.core.security;

import lingvo.movie.core.security.utils.UserPrincipalWithId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;



/**
 * Created by yaroslav on 02.05.15.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired  UserDetailsService dataBaseUserDetailsService;
    @Resource List<AccessDecisionVoter<?>> decisionVoters;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(dataBaseUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/**/users").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/public/**").permitAll()
                .antMatchers("/**").hasRole("ADMIN")
                .accessDecisionManager(new AffirmativeBased(decisionVoters))
                .and()
                .formLogin()
                .loginProcessingUrl("/login").permitAll()
                .successHandler((request, response, authentication) -> {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getOutputStream().print(((UserPrincipalWithId) authentication.getPrincipal()).getId());})
                .and()
                .rememberMe().key("lingvo-movie").tokenValiditySeconds(3600*24)
                //.rememberMe().rememberMeServices(new TokenBasedRememberMeServices("lingvo-movie", dataBaseUserDetailsService))
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((req, res, authExc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied"));
    }

    @Override
    public void setAuthenticationConfiguration(AuthenticationConfiguration authenticationConfiguration) {
        super.setAuthenticationConfiguration(authenticationConfiguration);
    }
}
