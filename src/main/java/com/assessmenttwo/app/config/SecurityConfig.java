package com.assessmenttwo.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Bean
    SecurityFilterChain formSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests()
                    .antMatchers("/error", "/401", "/404", "/500").permitAll()
                    .antMatchers("/js/**","/css/**","/img/**", "/users/register").permitAll()
                    .antMatchers("/customers/**").hasAnyAuthority("CUSTOMER_PRIVILEGE")
                    .antMatchers("/products/**").hasAnyAuthority("PRODUCT_PRIVILEGE")
                    .antMatchers("/orders/**").hasAnyAuthority("ORDER_PRIVILEGE")
                    .antMatchers("/users/manage").hasAnyAuthority("USER_PRIVILEGE")
                    .antMatchers("/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .and()
                .logout()
                    .permitAll()
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .and()
                .exceptionHandling()
                    .accessDeniedHandler(accessDeniedHandler())
                    .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (httpServletRequest, httpServletResponse, e) -> {
            // You can perform custom handling here, e.g., redirect to a custom error page
            httpServletResponse.sendRedirect("/401");
        };
    }
}
