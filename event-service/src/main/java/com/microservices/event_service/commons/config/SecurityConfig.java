package com.microservices.event_service.commons.config;


import com.microservices.event_service.commons.jwt.JwtAuthorizationFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    protected static final String[] PUBLIC_URLS = {"/swagger-ui/**", "/v3/api-docs/**", "/h2-console/**"}; //{"/v1/admin/event/**"};

    private final JwtAuthorizationFilter jwtAuthorizationFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpServletRequest request, HttpServletResponse response, HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sessionConfig ->
                        sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(a -> a.requestMatchers(PUBLIC_URLS).permitAll().anyRequest().authenticated())

                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        //.formLogin(Customizer.withDefaults());

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    /**
     * In Memory AUthentication
     *
     */

   /* @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {

        UserDetails userDetails = User.builder()
                .username("rahul")
                .password(passwordEncoder.encode("rahul"))
                .roles(RoleType.USER.name())
                .build();

       UserDetails userDetails1 = User.builder()
                .username("rahul1")
                .password(passwordEncoder.encode("rahul"))
                .roles(RoleType.USER.name())
                .build();


        return new InMemoryUserDetailsManager(userDetails,userDetails1);
    }*/
}
