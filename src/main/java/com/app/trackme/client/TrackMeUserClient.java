package com.app.trackme.client;

import com.app.trackme.interceptor.AuthRequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "trackme-user", configuration = AuthRequestInterceptor.class)
public interface TrackMeUserClient {

    @GetMapping("/users/username")
    String getUsername();
}
