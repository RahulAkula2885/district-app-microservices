package com.microservices.user_service.commons.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.microservices.user_service.commons.security.model.UserPrinciple;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.microservices.user_service.commons.security.constants.SecurityConstants.*;
import static java.util.Arrays.stream;

@Component
public class JWTTokenProvider {

    @Value("${jwt.secret}")
    public String jwtSecret;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateJWTToken(UserPrinciple userPrinciple) {

        Map<String, Object> claims = new HashMap<>();
        List<String> authorities = userPrinciple.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        claims.put(AUTHORITIES, authorities); // Store authorities as a list

        return Jwts.builder()
                .issuer(ISSUER)
                .subject(userPrinciple.getUsername())
                .claim(USER_ID, userPrinciple.getUserId())
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
                .signWith(getSecretKey())
                .compact();
    }

   /* private String[] getClaimsFromUser(UserPrinciple userPrinciple) {
        List<String> authorities = new ArrayList<>();
        for (GrantedAuthority grantedAuthority : userPrinciple.getAuthorities()) {
            authorities.add(grantedAuthority.getAuthority());
        }
        return authorities.toArray(new String[0]);
    }*/

    public List<GrantedAuthority> getAuthorities(String token) {
        String[] claims = getClaimsFromToken(token);
        return stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toUnmodifiableList());
    }

    public Authentication getAuthentication(String username, List<GrantedAuthority> authorities, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, getUserIdFromRequest(request), authorities);
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return usernamePasswordAuthenticationToken;
    }

    private Long getUserIdFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = authorizationHeader.substring(TOKEN_PREFIX.length());
        return getClaimUserIdFromToken(token);
    }

    private Long getClaimUserIdFromToken(String token) {
        JWTVerifier jwtVerifier = getJWTVerifier();
        return jwtVerifier.verify(token).getClaim(USER_ID).asLong();
    }

    public String getSubject(String token) {
        JWTVerifier jwtVerifier = getJWTVerifier();
        return jwtVerifier.verify(token).getSubject();
    }

    public boolean isTokenValid(String username, String token) {
        JWTVerifier jwtVerifier = getJWTVerifier();
        return StringUtils.hasText(username) && !isTokenExpired(jwtVerifier, token);
    }

    private boolean isTokenExpired(JWTVerifier jwtVerifier, String token) {
        Date expiration = jwtVerifier.verify(token).getExpiresAt();
        return expiration.before(new Date());
    }

    private String[] getClaimsFromToken(String token) {
        JWTVerifier jwtVerifier = getJWTVerifier();
        return jwtVerifier.verify(token).getClaim(AUTHORITIES).asArray(String.class);
    }

    private JWTVerifier getJWTVerifier() {
        JWTVerifier jwtVerifier;
        try {
            Algorithm algorithm = Algorithm.HMAC512(jwtSecret);
            jwtVerifier = JWT.require(algorithm).withIssuer(ISSUER).build();
        } catch (JWTVerificationException _) {
            throw new JWTVerificationException("Token cannot be verified");
        }
        return jwtVerifier;
    }

}
