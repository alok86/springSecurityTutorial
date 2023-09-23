package com.softwarecafe.springSecurityTutorial.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET = "GoingToGenerateSuperStrongKeyForBase64265BiteLongWithSpecialKeyAndWillWorkWithIt";

    public String generateToken(String userName) {
        Map<String , Object> claim = new HashMap<>();
        return this.createToken(claim,userName);
    }

    private String createToken(Map<String , Object> claims , String userName){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*2))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keysByte = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keysByte);
    }

    public String extractUsername(String token) {

        return extractClaim(token , Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims , T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Date extractExpiration(String token){
        Date date = extractClaim(token, Claims::getExpiration);

        return date;
    }
    private  boolean isTokenExpired(String token){

        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUsername(token);
        boolean b = userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
        return b;
    }
}
