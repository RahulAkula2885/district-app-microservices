package com.microservices.user_service.model.request;

import com.microservices.user_service.model.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

    private String userName;
    private String email;
    private String password;
    private String about;
    private Integer countryCode;
    private String mobileNumber;
    private Set<RoleType> roleTypes = new HashSet<>();
}
