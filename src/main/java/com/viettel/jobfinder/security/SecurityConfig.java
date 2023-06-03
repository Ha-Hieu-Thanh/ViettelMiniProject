package com.viettel.jobfinder.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.viettel.jobfinder.security.filter.AuthenticationFilter;
import com.viettel.jobfinder.security.filter.ExceptionHandlerFilter;
import com.viettel.jobfinder.security.filter.JWTAuthorizationFilter;
import com.viettel.jobfinder.security.manager.CustomAuthenticationManager;
import com.viettel.jobfinder.shared.annotation.Public;

import lombok.AllArgsConstructor;

import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    private CustomAuthenticationManager customAuthenticationManager;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(customAuthenticationManager);

        http
                .headers().frameOptions().disable() // New Line: the h2 console runs on a "frame". By default, Spring
                                                    // Security prevents rendering within an iframe. This line disables
                                                    // its prevention
                .and()
                .csrf().disable()
                .authorizeRequests()
                // permit all swagger
                // .antMatchers("/v3/api-docs/**", "/configuration/**", "/swagger*/**",
                // "/webjars/**")
                // .permitAll()
                .anyRequest().permitAll()
                .and()
                .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
                .addFilter(authenticationFilter)
                .addFilterAfter(new JWTAuthorizationFilter(), AuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

}