package com.microservices.user_service.controller;

import com.microservices.user_service.model.request.LoginRequest;
import com.microservices.user_service.model.request.SignUpRequest;
import com.microservices.user_service.model.response.CommonResponse;
import com.microservices.user_service.model.response.PaginationResponse;
import com.microservices.user_service.service.IUserAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/admin/users")
@RequiredArgsConstructor
public class UserAdminController {

    private final IUserAdminService iUserAdminService;

    //@PostMapping(path = "/create", value = MediaType.APPLICATION_XML_VALUE)
    @PostMapping("/create")
    public ResponseEntity<CommonResponse> createUser(@RequestBody SignUpRequest signUpRequest) {
        return iUserAdminService.createUser(signUpRequest);
    }

    @PostMapping("login")
    public ResponseEntity<CommonResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        return iUserAdminService.loginUser(loginRequest);
    }

    @GetMapping("/details")
    public PaginationResponse getAllUserDetails(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                                                @RequestParam(value = "size", defaultValue = "10") int pageSize) {

        Pageable pageable = PageRequest.of((pageNumber - 1), pageSize);
        return iUserAdminService.getAllUserDetails(pageable);
    }

}