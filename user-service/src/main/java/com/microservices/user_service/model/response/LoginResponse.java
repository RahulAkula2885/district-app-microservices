package com.microservices.user_service.model.response;

import com.microservices.user_service.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    public String jwt;
    public User details;
}
