package com.hourglass.schedule.controller;

import com.hourglass.common.response.Response;
import com.hourglass.schedule.response.SeatQueryResponse;
import com.hourglass.schedule.service.SeatService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
