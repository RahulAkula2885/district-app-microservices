package com.microservices.user_service;

import com.microservices.user_service.commons.utils.CommonUtil;
import com.microservices.user_service.entity.User;
import com.microservices.user_service.model.enums.RoleType;
import com.microservices.user_service.model.request.SignUpRequest;
import com.microservices.user_service.model.response.CommonResponse;
import com.microservices.user_service.repository.IUserRepository;
import com.microservices.user_service.service.UserAdminServiceImpl;
import com.microservices.user_service.service.validations.UserValidations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Nested
@ExtendWith(MockitoExtension.class)
class UserAdminServiceImplTest {

    @Mock
    IUserRepository iUserRepository;

    @Mock
    UserValidations validations;

    @Mock
    CommonUtil commonUtil;

    @Mock
    CommonResponse commonResponse;

    @InjectMocks
    UserAdminServiceImpl userAdminService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void myFirstTest() {
        IO.println("This is my 1st unit test");
    }

    public UserAdminServiceImplTest() {
        MockitoAnnotations.openMocks(this);
       // mockMvc = MockMvcBuilders.standaloneSetup(userAdminService).build();
    }


    @Test
    public void testCreateUser_Success() {

        User user = new User();
        user.setUserName("Rahul");
        user.setEmail("rahul@gmail.com");
        user.setPassword("1234");
        user.setAbout("Hi This is Rahul");
        user.setRoles(Set.of(RoleType.USER, RoleType.ADMIN));
        user.setCountryCode(91);
        user.setMobileNumber("99");
        user.setEnabled(true);
        user.setDeleted(false);
        user.setCreatedTime(Instant.now());
        user.setModifiedTime(Instant.now());

        // Log the user object for debugging
        System.out.println("User to save: " + user);

        // Mock the repository to return the user when save is called
        when(iUserRepository.save(any(User.class))).thenReturn(user);

        // Call the method that creates the user
        User response = userAdminService.createTestUser(user);

        // Log the response for debugging
        System.out.println("Response: " + response);

        // Assertions
        Assertions.assertNotNull(response); // Ensure response is not null
        Assertions.assertEquals(user.getId(), response.getId()); // Check if IDs match
    }


    @Test
    public void testCreateUser_Success1() throws Exception {

        SignUpRequest signUpRequest = new SignUpRequest();

        signUpRequest.setUserName("Rahul");
        signUpRequest.setAbout("Hi This is Rahul");
        signUpRequest.setEmail("rahul@Gmail.com");
        signUpRequest.setPassword("1234");
        signUpRequest.setCountryCode(91);
        signUpRequest.setMobileNumber("99631787966");
        signUpRequest.setRoleTypes(Set.of(RoleType.USER, RoleType.ADMIN));

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

        IO.println("Users:- " + user);

        User savedUser = iUserRepository.save(user);
        System.out.println("Saved User: " + savedUser);

        when(iUserRepository.save(user)).thenReturn(user);
        //when(iUserRepository.save(any(User.class))).thenReturn(user);

        ResponseEntity<CommonResponse> commonResponse = userAdminService.createUser(signUpRequest);

        IO.println("CommonResponse:- " + commonResponse);

        Assertions.assertEquals(user.getId(), savedUser.getId());

        //Test Product = Matched Product

    }

    @Test
    public void testCreateUser_Failure() throws Exception {

    }
}
