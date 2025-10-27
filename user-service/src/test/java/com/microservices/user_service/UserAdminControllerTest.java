package com.microservices.user_service;


import com.microservices.user_service.controller.UserAdminController;
import com.microservices.user_service.model.enums.RoleType;
import com.microservices.user_service.model.request.SignUpRequest;
import com.microservices.user_service.model.response.CommonResponse;
import com.microservices.user_service.service.IUserAdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private IUserAdminService iUserAdminService;

    @InjectMocks
    private UserAdminController userAdminController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userAdminController).build();
    }

    @Test
    public void testCreateUser_Success() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("username", "email@example.com", "password", "about", 91, "9963178796", Set.of(RoleType.USER));
        CommonResponse commonResponse =  CommonResponse.builder()
                .status(true)
                .message("SUCCESS")
                .responseTime(Instant.now())
                .build();

        when(iUserAdminService.createUser(any(SignUpRequest.class))).thenReturn(new ResponseEntity<>(commonResponse, HttpStatus.OK));

        mockMvc.perform(post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\":\"username\",\"email\":\"email@example.com\",\"password\":\"password\",\"about\":\"about\",\"roleTypes\":\"role\",\"countryCode\":\"countryCode\",\"mobileNumber\":\"mobileNumber\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"));
    }

    @Test
    public void testCreateUser_Failure() throws Exception {
        when(iUserAdminService.createUser(any(SignUpRequest.class))).thenThrow(new RuntimeException("User creation failed"));

        mockMvc.perform(post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\":\"username\",\"email\":\"email@example.com\",\"password\":\"password\",\"about\":\"about\",\"roleTypes\":\"role\",\"countryCode\":\"countryCode\",\"mobileNumber\":\"mobileNumber\"}"))
                .andExpect(status().isInternalServerError());
    }

}
