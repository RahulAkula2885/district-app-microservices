package com.microservices.event_service.commons;


public class SecurityConstants {

    private SecurityConstants(){
    }

    public static final String TOKEN_PREFIX ="Bearer ";
    public static final String ISSUER = "Rahul";
    public static final String AUTHORITIES = "authorities";
    public static final String USER_ID = "userId";
}
