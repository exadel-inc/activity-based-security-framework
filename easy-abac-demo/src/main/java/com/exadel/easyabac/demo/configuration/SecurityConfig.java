package com.exadel.easyabac.demo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * The application security configuration.
 *
 * @author Gleb Bondarchuk
 * @author Igor Sych
 * @since 1.0-RC1
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/*", "/welcome", "/login-as-user", "/login-as-administrator").permitAll()
                .anyRequest()
                .authenticated();
    }
}
