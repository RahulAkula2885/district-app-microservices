package com.microservices.movie_service.commons.exceptions;

public class CustomException extends RuntimeException{

    public CustomException(String message){
        super(message);
    }
}
