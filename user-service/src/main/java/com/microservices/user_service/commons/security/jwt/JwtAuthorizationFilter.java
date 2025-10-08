package com.microservices.user_service.commons.security.jwt;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.microservices.user_service.commons.exceptions.CustomException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static com.microservices.user_service.commons.security.constants.SecurityConstants.TOKEN_PREFIX;


@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JWTTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("incoming request: {}", request.getRequestURI());

        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            response.setStatus(HttpStatus.OK.value());
        } else {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_PREFIX)) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = authorizationHeader.substring(TOKEN_PREFIX.length());
            try {
                String username = jwtTokenProvider.getSubject(token);

                if (jwtTokenProvider.isTokenValid(username, token) && SecurityContextHolder.getContext().getAuthentication() == null) {
                    List<GrantedAuthority> authorities = jwtTokenProvider.getAuthorities(token);
                    Authentication authentication = jwtTokenProvider.getAuthentication(username, authorities, request);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    SecurityContextHolder.clearContext();
                }
            } catch (TokenExpiredException e) {
                log.warn("Token expired: {}", e.getMessage());
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Token has expired");
                throw new CustomException("Token expired: {}" + e.getMessage());
                //return;
            } catch (SignatureVerificationException e) {
                log.warn("Invalid token signature: {}", e.getMessage());
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid token signature");
                //return;
                throw new CustomException("Invalid token signature: {}" + e.getMessage());
            } catch (Exception e) {
                log.error("Token validation error: {}", e.getMessage());
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Token validation error");
                //return;
                throw new CustomException("Token validation error: {}" + e.getMessage());
            }

            filterChain.doFilter(request, response);
        }
    }

    /*protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("incoming request: {}", request.getRequestURI());

        if(request.getMethod().equalsIgnoreCase("OPTIONS")){
            response.setStatus(HttpStatus.OK.value());
        }else{
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if(authorizationHeader ==  null || !authorizationHeader.startsWith(TOKEN_PREFIX)){
                filterChain.doFilter(request,response);
                return;
            }

            String token = authorizationHeader.substring(TOKEN_PREFIX.length());
            String username = jwtTokenProvider.getSubject(token);

            if(jwtTokenProvider.isTokenValid(username,token) && SecurityContextHolder.getContext().getAuthentication() == null){
                List<GrantedAuthority> authorities = jwtTokenProvider.getAuthorities(token);
                Authentication authentication = jwtTokenProvider.getAuthentication(username,authorities,request);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else{
                SecurityContextHolder.clearContext();
            }
            filterChain.doFilter(request,response);
        }
    }*/
}
