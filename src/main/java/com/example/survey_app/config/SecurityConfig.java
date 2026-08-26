package com.example.survey_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.survey_app.service.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Sonsuz döngü (StackOverflow) hatasını kesin olarak çözen manuel ProviderManager tanımı:
    @Bean
    public AuthenticationManager authenticationManager(UserDetailsServiceImpl userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        
        return new ProviderManager(authProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
           .csrf(csrf -> csrf.disable())
           // H2 konsolunun düzgün açılabilmesi için frame korumasını kapatıyoruz
           .headers(headers -> headers.frameOptions(frame -> frame.disable()))
           .authorizeHttpRequests(auth -> auth
            .requestMatchers("/User/user", "/api/auth/login", "/login.html", "/register.html", "/*.html", "/h2-console/**").permitAll()
            .anyRequest().authenticated()
           )
           .exceptionHandling(exception -> exception
            .authenticationEntryPoint(customAuthenticationEntryPoint)
           );
        return http.build();
    }
}