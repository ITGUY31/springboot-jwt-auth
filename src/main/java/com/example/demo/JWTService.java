package com.example.demo;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;


@Service
public class JWTService {
	
	
	// private static final String SECRET_KEY = "012ddd97e3df8ecd396092964fa9eedbb76a482634189e79344ee0975deed105";

	
	 @Value("${jwt.secret}")
	 private String SECRET_KEY;
	 
	
	    public String generateToken(String username) {
	        return Jwts.builder()
	                .setSubject(username)                           // Subject (e.g., username)
	                .setIssuedAt(new Date())                        // Issue time
	                .setExpiration(new Date(System.currentTimeMillis() + 600000 ))
	                .signWith(getSignInKey())
	                //.signWith(SignatureAlgorithm.HS256, SECRET_KEY) // Signing key
	                .compact();
	    }
	    
	   
	    
	    private Key getSignInKey() {
			
			byte[] keyByte = Decoders.BASE64.decode(SECRET_KEY);
			
			System.out.println("lenght: " + keyByte.length);
			
			return Keys.hmacShaKeyFor(keyByte);
		}
	    
	    
	   

}
