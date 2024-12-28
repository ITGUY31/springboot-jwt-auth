package com.example.demo;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class JwtController {

	
	 @Autowired
	 JWTService jwtService;
	 
	 
	 @Autowired
	 JwtValidationService jwtValidationService;
	 
	 



	    @GetMapping("/generate-token")
	    public String generateToken(@RequestParam String username) {
	        return jwtService.generateToken(username);
	    }
	    
	    
	    @GetMapping("/validate-token")
	    public String validateToken(HttpServletRequest request) {
	        // Check if the security context has a valid authentication object
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	        
	        System.out.println("Authorisation Obj {}" + authentication);
	        // If authentication is present, that means the token is valid
	        if (authentication != null && authentication.isAuthenticated()) {
	            return "Token is valid! User: " + authentication.getName();
	        } else {
	            return "Invalid token!";
	        }
	    }    
	    
	    
	    
	    
	    
	    
	    
	    
//	    @GetMapping("/validate-token")
//	    public String validateToken(HttpServletRequest request, HttpServletResponse response) {
//	      
//	    	try {
//	    		jwtAuthenticationFilter jwtAuthenticationFilter = new jwtAuthenticationFilter();
//				jwtAuthenticationFilter.doFilterInternal(request, response, new FilterChain() {
//
//					@Override
//					public void doFilter(ServletRequest request, ServletResponse response)
//							throws IOException, ServletException {
//						System.out.println("Inside normal doFilter");
//						
//					}});
//				
//				if (SecurityContextHolder.getContext().getAuthentication() != null) {
//	                return "Token is valid!";
//	            } else {
//	                return "Invalid token!";
//	            }
//				
//	    	} catch (Exception e) {
//	            return "Error validating token: " + e.getMessage();
//	        }
	    	
	    }
	

