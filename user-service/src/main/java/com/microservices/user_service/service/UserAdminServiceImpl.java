package com.microservices.user_service.service;

import com.microservices.user_service.entity.User;
import com.microservices.user_service.model.request.LoginRequest;
import com.microservices.user_service.model.request.SignUpRequest;
import com.microservices.user_service.model.response.CommonResponse;
import com.microservices.user_service.model.response.PaginationResponse;
import com.microservices.user_service.commons.utils.CommonUtil;
import com.microservices.user_service.repository.IUserRepository;
import com.microservices.user_service.service.validations.UserValidations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAdminServiceImpl implements IUserAdminService {

    private final IUserRepository iUserRepository;
    private final UserValidations validations;
    private final CommonUtil commonUtil;


    @Override
    public ResponseEntity<CommonResponse> createUser(SignUpRequest signUpRequest) {

        validations.createAdminUser(signUpRequest);

        User user = User.builder()
                .userName(signUpRequest.getUserName())
                .email(signUpRequest.getEmail())
                .password(signUpRequest.getPassword())
                .about(signUpRequest.getAbout())
                .roles(signUpRequest.getRoleTypes())
                .countryCode(signUpRequest.getCountryCode())
                .mobileNumber(signUpRequest.getMobileNumber())
                .enabled(true)
                .deleted(false)
                .createdTime(Instant.now())
                .modifiedTime(Instant.now())
                .build();

        user = iUserRepository.save(user);

        CommonResponse commonResponse = commonUtil.buildCommonResponse("SUCCESS", user);
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CommonResponse> loginUser(LoginRequest loginRequest) {
        User user = validations.loginUser(loginRequest);

        CommonResponse commonResponse = commonUtil.buildCommonResponse("SUCCESS", user);
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    @Override
    public PaginationResponse getAllUserDetails(Pageable pageable) {

        Page<User> users = iUserRepository.findAllUsers(pageable);
        return PaginationResponse.builder()
                .totalCount(users.getTotalElements())
                .response(users.getContent())
                .build();
    }
}
