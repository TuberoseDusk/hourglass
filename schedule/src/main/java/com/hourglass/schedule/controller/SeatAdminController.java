package com.hourglass.schedule.controller;

import com.hourglass.common.response.Response;
import com.hourglass.schedule.request.SeatStateUpdateRequest;
import com.hourglass.schedule.response.SeatQueryResponse;
import com.hourglass.schedule.service.SeatService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seat")
public class SeatAdminController {
    @Resource
    private SeatService seatService;

    /**
     *按车次id查询所有座位信息
     */
    @GetMapping("/querySeat/{dailyTrainId}")
    public Response<List<SeatQueryResponse>> querySeat(@PathVariable Long dailyTrainId) {
        return Response.success(seatService.query(dailyTrainId));
    }

    @GetMapping("/querySeatSingle/{dailyTrainId}/{seatNumber}")
    public Response<SeatQueryResponse> querySeatSingle(@PathVariable Long dailyTrainId, @PathVariable Integer seatNumber) {
        return Response.success(seatService.query(dailyTrainId, seatNumber));
    }

    @PutMapping("/updateState")
    public Response<Void> updateState(@Valid @RequestBody SeatStateUpdateRequest seatStateUpdateRequest) {
        seatService.updateState(seatStateUpdateRequest);
        return Response.success();
    }
}
