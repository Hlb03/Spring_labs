package com.lab2.electronicQueue.configuration;

import com.lab2.electronicQueue.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomBCryptPasswordEncoder customBCryptPasswordEncoder;

    @Autowired
    public SecurityConfig(CustomUserDetailsService customUserDetailsService, CustomBCryptPasswordEncoder customBCryptPasswordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.customBCryptPasswordEncoder = customBCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/user/**","/record/**","/host/**").authenticated()
                    .antMatchers("/admin/**").hasRole(UserRole.ADMIN.name())
                    .anyRequest()
                    .permitAll()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .and()
                .logout()
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutSuccessUrl("/")
                    .permitAll()
                    .and()
                .sessionManagement()
                    .invalidSessionUrl("/")
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(true);
    }

    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(customBCryptPasswordEncoder.passwordEncoder());
        return daoAuthenticationProvider;
    }


}
