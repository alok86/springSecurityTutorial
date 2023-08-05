package com.softwarecafe.springSecurityTutorial.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    private static final String SECRET = "198f70d4d3abee3521e6503c319510615d51fe46bb1efda8b99542c1289ef11e";

    public String generateToken(String userName) {
        Map<String , Object> claim = new HashMap<>();
        return this.createToken(claim,userName);
    }

    private String createToken(Map<String , Object> claims , String userName){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()+1000*60*30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keysByte = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keysByte);
    }
}
