package com.microservices.user_service.service;

import com.microservices.user_service.entity.User;
import com.microservices.user_service.model.request.LoginRequest;
import com.microservices.user_service.model.request.SignUpRequest;
import com.microservices.user_service.model.response.CommonResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface IUserAppService {
    ResponseEntity<CommonResponse> createUser(SignUpRequest signUpRequest);

    ResponseEntity<CommonResponse> loginUser(LoginRequest loginRequest);

    User getUserDetails();

    void voidResponse(HttpServletResponse response, HttpServletResponse response1) throws IOException;

    User getUserDetailsByUserId(Long userId);

    List<User> getUsersByIds(List<Long> ids);
}
