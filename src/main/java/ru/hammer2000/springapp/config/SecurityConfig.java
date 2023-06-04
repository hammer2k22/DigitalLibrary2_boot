package ru.hammer2000.springapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.hammer2000.springapp.services.UsersDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final UsersDetailsService usersDetailsService;

    @Autowired
    public SecurityConfig(UsersDetailsService usersDetailsService) {
        this.usersDetailsService = usersDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //Set authentication
        http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(usersDetailsService)
                .passwordEncoder(getPasswordEncoder());

        // Set authorization
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/admin","books/**","people/**").hasRole("ADMIN")
                .requestMatchers("/auth/**", "/error").permitAll()
                .anyRequest().hasAnyRole("USER", "ADMIN"));

        // Set custom login page
        http.formLogin(formLogin -> formLogin
                .loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/auth/login?error"));

        // Set logout
        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/auth/login"));

        return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}