package com.example.demo;

import java.io.IOException;
import java.security.Key;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class jwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	JwtValidationService JwtValidationService;
	
	 @Value("${jwt.secret}")
	 private String SECRET_KEY;
	 
	 private Key getSignInKey() {
			
			byte[] keyByte = Decoders.BASE64.decode(SECRET_KEY);
			
		//	System.out.println("lenght: " + keyByte.length);
			
			return Keys.hmacShaKeyFor(keyByte);
		}

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		 logger.error("Request URI: {}" + request.getRequestURI());
		
		final String authorizationHeader = request.getHeader("Authorization");
		
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
		
		
		String token = authorizationHeader.substring(7); 
		
		 logger.error("Extracted Token: {} " + token);
		
		 try {
			 Claims claims = Jwts.parser()
	                    .setSigningKey(getSignInKey())
	                    .parseClaimsJws(token)
	                    .getBody();
			 
			 
			 String username = claims.getSubject();
			 
			 logger.error("Extracted Username: {} " + username);
			 
			 
			 if (username != null && SecurityContextHolder.getContext().getAuthentication() == null && JwtValidationService.validateToken(token)) {
	              
				 logger.error("Token is valid, setting authentication context.");
				 
				 UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, null);
					
				 authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				 SecurityContextHolder.getContext().setAuthentication(authToken);
				 
				 logger.error("{} 2.");
				 
	            }
	                    
		
		 }catch (Exception e) {
			 
			   logger.error("JWT validation failed: {}" + e.getMessage());
	         //   response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired JWT token.");
	            
			    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	            response.getWriter().write("Invalid or expired JWT token.");
	            return;
	        }
		 
		 
		 logger.error("Proceeding with the filter chain.");
		 filterChain.doFilter(request, response);
	}

}
