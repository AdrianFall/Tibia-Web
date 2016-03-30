package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import rest.authentication.RestAuthenticationEntryPoint;

import javax.annotation.Resource;

/**
 * Created by Adrian on 29/03/2016.
 */

/*
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String REALM = "Secured Environment";

    @Resource
    private UserRepository userRepository;

    @Bean
    public BasicAuthenticationEntryPoint basicAuthenticationEntryPoint() {
        RestAuthenticationEntryPoint restAuthenticationEntryPoint = new RestAuthenticationEntryPoint();
        restAuthenticationEntryPoint.setRealmName(SpringSecurityConfiguration.REALM);
        return restAuthenticationEntryPoint;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userRepository);
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
// @formatter:off
        http
                .exceptionHandling().authenticationEntryPoint(basicAuthenticationEntryPoint())
                .and()
                .sessionManagement().enableSessionUrlRewriting(false).sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/api
*").permitAll()
                .and()
                .authorizeRequests().antMatchers("/api
*").authenticated()
                .and()
                .httpBasic().authenticationEntryPoint(basicAuthenticationEntryPoint())
                .and().csrf().disable();
// @formatter:on
    }

}
*/
