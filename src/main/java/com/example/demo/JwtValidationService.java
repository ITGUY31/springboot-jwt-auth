package com.example.demo;

import java.nio.charset.StandardCharsets;
import java.security.Key;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class JwtValidationService {
	
	
	
	 @Value("${jwt.secret}")
	 private String SECRET_KEY;
	 
	 
	 public boolean validateToken(String token) {
		 
		System.out.println("Inside validate token");
		 
		 try {
		 Jwts.parserBuilder()
         .setSigningKey(getSignInKey())
         .build()
         .parseClaimsJws(token);
		 
		 
		 
		return true;
		 }
		 catch(JwtException | IllegalArgumentException e) {
			 System.out.println("JWT claims string is empty: " + e.getMessage());
		 }
		 
		 return false;
	 }
	 
	 private Key getSignInKey() {
			
			byte[] keyByte = Decoders.BASE64.decode(SECRET_KEY);
			
		//	System.out.println("lenght: " + keyByte.length);
			
			return Keys.hmacShaKeyFor(keyByte);
		}
	 
	 
	 
	 
	 
	 
//	 public Claims getClaims(String token) {
//	        try {
//	            // Extract claims if the token is valid
//	            return Jwts.parserBuilder()
//	                       .setSigningKey(SECRET_KEY.getBytes())
//	                       .build()
//	                       .parseClaimsJws(token)
//	                       .getBody();
//	        } catch (JwtException | IllegalArgumentException e) {
//	            System.out.println("Unable to extract claims: " + e.getMessage());
//	            return null;
//	        }
//	    }
}
