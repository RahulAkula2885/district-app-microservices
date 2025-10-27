package com.microservices.movie_service.commons.interceptors;


import com.microservices.movie_service.commons.jwt.JWTTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Instant;

@Component
@Slf4j
public class MovieInterceptor implements HandlerInterceptor {

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String ISSUER = "Rahul";
    public static final String AUTHORITIES = "authorities";
    public static final String USER_ID = "userId";

    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        IO.println("Pre Handlee");

        log.info("incoming request: {}", request.getRequestURI());

        final String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            String jwtToken = authorizationHeader.substring(TOKEN_PREFIX.length());
            String username = jwtTokenProvider.getSubject(jwtToken);

            if (username != null && jwtTokenProvider.isTokenValid(username, jwtToken)) {
                // Token is valid, proceed with the request
                return true;
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
                return false;
            }
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization header is missing or invalid");
            //response.getWriter().write("Authorization");

            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            String finalResponse = """
                {
                    "status": %b,
                    "message": "%s",
                    "response": %s,
                    "responseTime": "%s"
                }
                """.formatted(true, "Authorization header is missing or invalid", null, Instant.now());

            response.getWriter().write(finalResponse);
            return false;
        }
    }

    /*public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        IO.println("Post Handlee");
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        IO.println("After Complete");
    }*/
}
