package com.microservices.user_service.service.validations;


import com.microservices.user_service.entity.User;
import com.microservices.user_service.model.enums.RoleType;
import com.microservices.user_service.commons.exceptions.CustomException;
import com.microservices.user_service.model.request.LoginRequest;
import com.microservices.user_service.model.request.SignUpRequest;
import com.microservices.user_service.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserValidations {

    private final IUserRepository iUserRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    public void createUser(SignUpRequest signUpRequest) {
        if (!StringUtils.hasText(signUpRequest.getUserName())) {
            throw new CustomException("User name must not be null or empty");
        }
        if (!StringUtils.hasText(signUpRequest.getEmail())) {
            throw new CustomException("Email must not be null or empty");
        }
        if (!StringUtils.hasText(signUpRequest.getPassword())) {
            throw new CustomException("Password must not be null or empty");
        }
        if (signUpRequest.getCountryCode() == null || signUpRequest.getCountryCode() == 0) {
            throw new CustomException("Country code must not be null or 0");
        }
        if (!StringUtils.hasText(signUpRequest.getMobileNumber())) {
            throw new CustomException("Mobile number must not be null or empty");
        }
        if (signUpRequest.getRoleTypes().isEmpty()) {
            throw new CustomException("Roles must not be null or empty");
        }

        Optional<User> checkEmail = iUserRepository.findByEmailAndDeletedFalse(signUpRequest.getEmail());
        if (checkEmail.isPresent()) {
            throw new CustomException("Email Already exist");
        }

        Optional<User> checkMobile = iUserRepository.findByCountryCodeAndMobileNumber(signUpRequest.getCountryCode(), signUpRequest.getMobileNumber());
        if (checkMobile.isPresent()) {
            throw new CustomException("Mobile number already exist");
        }


    }

    public User loginUser(LoginRequest loginRequest) {

        if (!StringUtils.hasText(loginRequest.email())) {
            throw new CustomException("Email must not be null or empty");
        }
        if (!StringUtils.hasText(loginRequest.password())) {
            throw new CustomException("Password must not be null or empty");
        }
        Optional<User> user = iUserRepository.findByEmailAndDeletedFalse(loginRequest.email());
        if (user.isEmpty()) {
            throw new CustomException("Email you've entered is not registered");
        }
       /* Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword())
        );*/

        boolean valid = passwordEncoder.matches(loginRequest.password(), user.get().getPassword());
        if (!valid) {
            throw new CustomException("Invalid Credentials");
        }

        return user.get();
    }

    public void createAdminUser(SignUpRequest signUpRequest) {
        if (!StringUtils.hasText(signUpRequest.getUserName())) {
            throw new CustomException("User name must not be null or empty");
        }
        if (!StringUtils.hasText(signUpRequest.getEmail())) {
            throw new CustomException("Email must not be null or empty");
        }
        if (!StringUtils.hasText(signUpRequest.getPassword())) {
            throw new CustomException("Password must not be null or empty");
        }
        if (signUpRequest.getCountryCode() == null || signUpRequest.getCountryCode() == 0) {
            throw new CustomException("Country code must not be null or 0");
        }
        if (!StringUtils.hasText(signUpRequest.getMobileNumber())) {
            throw new CustomException("Mobile number must not be null or empty");
        }
        if (signUpRequest.getRoleTypes().isEmpty()) {
            throw new CustomException("Roles must not be null or empty");
        }
        if (signUpRequest.getRoleTypes().contains(RoleType.USER)) {
            throw new CustomException("User role is not accepted");
        }

        Optional<User> checkEmail = iUserRepository.findByEmailAndDeletedFalse(signUpRequest.getEmail());
        if (checkEmail.isPresent()) {
            throw new CustomException("Email Already exist");
        }
        Optional<User> checkMobile = iUserRepository.findByCountryCodeAndMobileNumber(signUpRequest.getCountryCode(), signUpRequest.getMobileNumber());
        if (checkMobile.isPresent()) {
            throw new CustomException("Mobile number already exist");
        }
    }
}
