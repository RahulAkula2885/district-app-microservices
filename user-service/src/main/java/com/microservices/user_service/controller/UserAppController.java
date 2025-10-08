package com.microservices.user_service.controller;

import com.microservices.user_service.entity.User;
import com.microservices.user_service.model.request.LoginRequest;
import com.microservices.user_service.model.request.SignUpRequest;
import com.microservices.user_service.model.response.CommonResponse;
import com.microservices.user_service.service.IUserAppService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/v1/app/users")
@RequiredArgsConstructor
public class UserAppController {

    private final IUserAppService iUserAppService;

    @PostMapping("/create")
    public ResponseEntity<CommonResponse> createUser(@RequestBody SignUpRequest signUpRequest) {
        return iUserAppService.createUser(signUpRequest);
    }

    @PostMapping("login")
    public ResponseEntity<CommonResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        return iUserAppService.loginUser(loginRequest);
    }

    @GetMapping("/details")
    public User getUserDetails() {
        return iUserAppService.getUserDetails();
    }

    @GetMapping("/details/{userId}")
    public User getUserDetailsByUserId(@PathVariable("userId") Long userId) {
        return iUserAppService.getUserDetailsByUserId(userId);
    }

    /**
     * Send Response in Void (As void doesn't return any result)
     * */

    @GetMapping
    public void voidResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        iUserAppService.voidResponse(response,response);
    }

    @GetMapping("/detailss/{userIds}")
    public List<User> getUserDetailsByUserIds(@PathVariable("userIds") String userIds) {
        // Split the userIds string into a list of Longs
        List<Long> ids = Arrays.stream(userIds.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toList());

        return iUserAppService.getUsersByIds(ids);
    }


}

