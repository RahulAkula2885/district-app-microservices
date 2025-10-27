package com.microservices.event_service.model.response;

import com.microservices.event_service.model.enums.RoleType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class UserResponse {

    private Long id;
    private String userName;
    private String email;
    private String password;
    private String about;
    private int countryCode;
    private String mobileNumber;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<RoleType> roles = new HashSet<>();
    private boolean enabled;
    private boolean deleted;
    @Temporal(TemporalType.TIMESTAMP)
    private Instant createdTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Instant modifiedTime;
}
