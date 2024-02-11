package com.hourglass.schedule.controller;

import com.hourglass.common.response.Response;
import com.hourglass.schedule.request.StationCreateRequest;
import com.hourglass.schedule.service.StationService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/station")
public class StationAdminController {

    @Resource
    private StationService stationService;

    /**
     *创建车站
     */
    @PostMapping("/createStation")
    public Response<Void> createStation(@Valid @RequestBody StationCreateRequest stationCreateRequest) {
        stationService.create(stationCreateRequest);
        return Response.success();
    }

}
