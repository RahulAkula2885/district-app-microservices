package com.microservices.event_service.service;

import com.microservices.event_service.model.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * If we don't know the name of service we can provide default url
 */
//@FeignClient(name = "USER-SERVICE1",path = "/v1/app/users",url = "http://localhost:9001")
@FeignClient(name = "USER-SERVICE", path = "/v1/app/users")
public interface UserFeignClient {

    @GetMapping("/details/{userId}")
    public UserResponse getUserDetailsByUserId(@PathVariable("userId") Long userId);

    @GetMapping("/detailss/{userIds}")
    public List<UserResponse> getUserDetailsByUserIds(@PathVariable("userIds") String userIds);
}
