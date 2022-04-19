package com.shiv.auth.jwt.util;

import com.shiv.auth.constants.KeyConstant;
import com.shiv.auth.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Service
public class JwtUtil {
    private String SECRET_KEY =KeyConstant.KEY;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateAccessToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(KeyConstant.NAME,user.getName());
        claims.put(KeyConstant.ROLE,user.getRole());
        claims.put(KeyConstant.MOBILE,user.getMobile());
        claims.put(KeyConstant.CREATED_ON,user.getCreatedOn());
        claims.put(KeyConstant.TOKEN_TYPE,KeyConstant.ACCESS_TOKEN);
        return createToken(claims, user.getEmail(), TimeUnit.SECONDS.toMillis(59));
    }

    public String generateRefreshToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(KeyConstant.TOKEN_TYPE,KeyConstant.REFRESH_TOKEN);
        return createToken(claims, user.getEmail(), TimeUnit.DAYS.toMillis(10));
    }

    private String createToken(Map<String, Object> claims, String subject,long expirationTime) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isAccessToken(String token){
        Claims claims= extractAllClaims(token);
        String tokenType= (String) claims.get(KeyConstant.TOKEN_TYPE);
        return tokenType!=null && tokenType.equals(KeyConstant.ACCESS_TOKEN);
    }
}
