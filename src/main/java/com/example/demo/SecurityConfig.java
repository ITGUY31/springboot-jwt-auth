package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
	
	@Autowired
	jwtAuthenticationFilter jwtAuthenticationFilter;
	
	
	  @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	        http.csrf(AbstractHttpConfigurer::disable)
	            // Disable CSRF since we are using stateless authentication
	            

	            // Set authorization rules
	            .authorizeHttpRequests(auth -> auth
	                .requestMatchers("/validate-token").authenticated() // Require authentication for validate-token endpoint
	                .anyRequest().permitAll() // Allow all other requests without authentication
	            )

	            // Enforce stateless session for APIs
	            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

	            // Add the JWT filter before UsernamePasswordAuthenticationFilter
	            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

	        return http.build();
	    }
	  
	  @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	        return authenticationConfiguration.getAuthenticationManager();
	    }

}
