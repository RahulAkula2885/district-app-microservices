package com.microservices.user_service.service;


import com.microservices.user_service.entity.User;
import com.microservices.user_service.commons.exceptions.CustomException;
import com.microservices.user_service.model.request.LoginRequest;
import com.microservices.user_service.model.request.SignUpRequest;
import com.microservices.user_service.model.response.CommonResponse;
import com.microservices.user_service.model.response.LoginResponse;
import com.microservices.user_service.commons.security.jwt.JWTTokenProvider;
import com.microservices.user_service.commons.security.model.UserPrinciple;
import com.microservices.user_service.commons.utils.CommonUtil;
import com.microservices.user_service.repository.IUserRepository;
import com.microservices.user_service.service.validations.UserValidations;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

import static com.microservices.user_service.commons.utils.CommonUtil.getCurrentUserId;


@Service
@RequiredArgsConstructor
public class UserAppServiceImpl implements IUserAppService {

    private final IUserRepository iUserRepository;
    private final UserValidations validations;
    private final CommonUtil commonUtil;
    private final PasswordEncoder passwordEncoder;
    private final JWTTokenProvider jwtTokenProvider;

    @Override
    public ResponseEntity<CommonResponse> createUser(SignUpRequest signUpRequest) {
        validations.createUser(signUpRequest);

        User user = User.builder()
                .userName(signUpRequest.getUserName())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
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

        UserPrinciple userPrinciple = new UserPrinciple(user, user.getRoles());
        String token = jwtTokenProvider.generateJWTToken(userPrinciple);

        LoginResponse loginResponse = LoginResponse.builder()
                .jwt(token)
                .details(user)
                .build();

        CommonResponse commonResponse = commonUtil.buildCommonResponse("SUCCESS", loginResponse);
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    @Override
    public User getUserDetails() {
        Long userId = getCurrentUserId();
        return iUserRepository.findById(userId).orElseThrow(() -> new CustomException("User not found with ID: " + userId));
    }

    @Override
    public void voidResponse(HttpServletResponse response, HttpServletResponse response1) throws IOException {

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String finalResponse = """
                {
                    "status": %b,
                    "message": "%s",
                    "response": %s,
                    "responseTime": "%s"
                }
                """.formatted(true, "SUCCESS", null, Instant.now());

        response.getWriter().write(finalResponse);
    }

    @Override
    public User getUserDetailsByUserId(Long userId) {
        return iUserRepository.findById(userId).orElseThrow(() -> new CustomException("User not found with ID: " + userId));
    }

    @Override
    public List<User> getUsersByIds(List<Long> ids) {
        return iUserRepository.findAllById(ids);
    }
}
