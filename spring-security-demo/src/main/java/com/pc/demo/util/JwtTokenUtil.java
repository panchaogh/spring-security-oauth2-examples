package com.pc.demo.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class JwtTokenUtil {
    private JwtTokenUtil() {
    }


    private static long tokenExpiration = 24 * 60 * 60 * 1000;
    private static String tokenSignKey = "123456";
    private static String authoritiesKey = "authorities";

    public static String createToken(String userName) {
        String token = Jwts.builder().setSubject(userName)
            .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
            .signWith(SignatureAlgorithm.HS512, tokenSignKey).compressWith(CompressionCodecs.GZIP).compact();
        return token;
    }

    public static String createToken(String userName, String authorities) {
        String token = Jwts.builder().setSubject(userName)
            .claim(authoritiesKey, authorities)
            .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
            .signWith(SignatureAlgorithm.HS512, tokenSignKey).compressWith(CompressionCodecs.GZIP).compact();
        return token;
    }

    public static Claims parseJWT(String token) {
        return Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody();
    }

    public static String getUserNameFromToken(String token) {
        String userName = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody().getSubject();
        return userName;
    }

    public static String getUserRoleFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody();
        return claims.get(authoritiesKey).toString();
    }
}
