package com.pavelmuravyev.accountservice.configurations;

import com.pavelmuravyev.accountservice.exceptionhandlers.CustomAccessDeniedHandler;
import com.pavelmuravyev.accountservice.eventhandlers.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    public SecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl,
                          RestAuthenticationEntryPoint restAuthenticationEntryPoint,
                          CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().authenticationEntryPoint(restAuthenticationEntryPoint)
            .and()
            .csrf().disable().headers().frameOptions().disable()
            .and()
            .authorizeRequests()
            .mvcMatchers(HttpMethod.GET, "api/admin/user").hasRole("ADMINISTRATOR")
            .mvcMatchers(HttpMethod.DELETE, "api/admin/**").hasRole("ADMINISTRATOR")
            .mvcMatchers(HttpMethod.PUT, "api/admin/user/role").hasRole("ADMINISTRATOR")
            .mvcMatchers(HttpMethod.PUT, "api/admin/user/access").hasRole("ADMINISTRATOR")
            .mvcMatchers(HttpMethod.POST, "api/acct/payments").hasRole("ACCOUNTANT")
            .mvcMatchers(HttpMethod.PUT, "api/acct/payments").hasRole("ACCOUNTANT")
            .mvcMatchers(HttpMethod.GET, "/api/security/events").hasRole("AUDITOR")
            .mvcMatchers(HttpMethod.GET, "/api/empl/payment").hasAnyRole("ACCOUNTANT", "USER")
            .mvcMatchers(HttpMethod.POST, "/api/auth/changepass").authenticated()
            .mvcMatchers(HttpMethod.POST, "/api/auth/signup", "/h2").permitAll()
            .and()
            .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler)
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl)
            .passwordEncoder(getBCryptPasswordEncoder());
    }

    @Bean
    public PasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*@Bean
    public AccessDeniedHandler getAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }*/

    /*@Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }*/

    /*@Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }*/
}
