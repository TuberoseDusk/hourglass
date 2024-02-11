package com.hourglass.schedule.controller;

import com.hourglass.common.response.Response;
import com.hourglass.schedule.request.DailySeatGenerateRequest;
import com.hourglass.schedule.service.DailyTrainService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/daily/train")
public class DailyTrainAdminController {
    @Resource
    private DailyTrainService dailyTrainService;

    /**
     *生成每日列车数据
     */
    @PostMapping("/generate")
    public Response<Void> generateDailyTrain(@Valid @RequestBody DailySeatGenerateRequest dailySeatGenerateRequest) {
        if (dailySeatGenerateRequest.getTrainCode() == null) {
            // 不指定列车号，生成全部列车数据
            dailyTrainService.generateAll(dailySeatGenerateRequest);
        } else {
            dailyTrainService.generate(dailySeatGenerateRequest);
        }
        return Response.success();
    }

}
