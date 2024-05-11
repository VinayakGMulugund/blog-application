package com.vinayak.mulugund.developer.Blogapplication.service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {
    private Set<String> revokedTokens = new HashSet<>();
    private final String SECRET_KEY = "lsjflsjbdlfjljnlkjnsdlkfjnslkdnfvinayakskkdjfnlkzjsfdlkjzsdlkfjnzsldkjfnlkzsdjfnlkjnlkjzsdlfkjn";
    private String currentToken;
    public String generateToken(String username) {
        Map<String, Claims> claims = new HashMap<>();

        return Jwts.builder().setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() * 1000*60*10))
                .signWith(getKey(), SignatureAlgorithm.HS256).compact();
    }

    private SecretKey getKey() {
        byte[] key = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(key);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build().parseClaimsJws(token).getBody();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        currentToken = token;
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token) && !isTokenRevoked(token);
    }

    private boolean isTokenExpired(String token) {
        Date current = new Date(System.currentTimeMillis());
        Date tokenExpiration = extractClaim(token, Claims::getExpiration);
        return current.after(tokenExpiration);
    }

    private boolean isTokenRevoked(String token) {
        return this.revokedTokens.contains(token);
    }

    public void revokeToken() {
        this.revokedTokens.add(this.currentToken);
    }
}
