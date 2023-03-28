package com.sns.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JwtTokenUtils {

    public static String getMemberName(String token, String key) {
        return extractClaims(token, key).get("memberName", String.class);
    }
    public static boolean isExpired(String token, String key) {
        Date expireDate = extractClaims(token, key).getExpiration();
        return expireDate.before(new Date());
    }

    private static Claims extractClaims(String token, String key) {
        return Jwts.parserBuilder().setSigningKey(getKey(key))
                .build().parseClaimsJws(token).getBody();
    }
    public static String generateToken(String memberName, String key, long expiredTimeMs) {
        Claims claims = Jwts.claims();
        claims.put("memberName", memberName);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() * expiredTimeMs))
                .signWith(getKey(key), SignatureAlgorithm.HS256)
                .compact();
    }

    public static Key getKey(String key) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
