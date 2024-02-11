package com.hourglass.schedule.controller;

import com.hourglass.common.response.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {
    @GetMapping("/hello")
    public Response<String> hello(@RequestParam(required = false) String visitor) {
        String greet = "hello, " + visitor + "!";
        return Response.success(greet);
    }
}
