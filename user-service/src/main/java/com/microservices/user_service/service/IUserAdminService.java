package com.microservices.user_service.service;

import com.microservices.user_service.model.request.LoginRequest;
import com.microservices.user_service.model.request.SignUpRequest;
import com.microservices.user_service.model.response.CommonResponse;
import com.microservices.user_service.model.response.PaginationResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface IUserAdminService {
    ResponseEntity<CommonResponse> createUser(SignUpRequest signUpRequest);

    ResponseEntity<CommonResponse> loginUser(LoginRequest loginRequest);

    PaginationResponse getAllUserDetails(Pageable pageable);
}
