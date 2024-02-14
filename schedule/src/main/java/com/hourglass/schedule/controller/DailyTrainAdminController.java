package com.hourglass.schedule.controller;

import com.hourglass.common.response.Response;
import com.hourglass.schedule.request.DailySeatGenerateRequest;
import com.hourglass.schedule.response.DailyStopResponse;
import com.hourglass.schedule.response.DailyTrainQueryResponse;
import com.hourglass.schedule.response.SectionQueryResponse;
import com.hourglass.schedule.service.DailyTrainService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

    /**
     * 查询可达线路
     */
    @GetMapping("/querySection/{startStation}/{endStation}/{date}")
    public Response<List<SectionQueryResponse>> querySection(@PathVariable String startStation,
                                                             @PathVariable String endStation,
                                                             @PathVariable(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        List<SectionQueryResponse> sectionQueryResponses = dailyTrainService.querySection(startStation, endStation, date);
        return Response.success(sectionQueryResponses);
    }

    /**
     * 按发车日期查询所有车次
     */
    @GetMapping("/queryDailyTrain/{date}")
    public Response<List<DailyTrainQueryResponse>> queryDailyTrain(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return Response.success(dailyTrainService.queryDailyTrain(date));
    }

    /**
     * 按id查询车次
     */
    @GetMapping("/queryDailyTrainById/{dailyTrainId}")
    public Response<DailyTrainQueryResponse> queryDailyTrainById(@PathVariable Long dailyTrainId) {
        return Response.success(dailyTrainService.queryDailyTrainById(dailyTrainId));
    }

    /**
     * 按车次id和停站序号查停站信息
     */
    @GetMapping("/queryDailyStop/{dailyTrainId}/{stopIndex}")
    public Response<DailyStopResponse> queryDailyStop(@PathVariable Long dailyTrainId, @PathVariable Integer stopIndex) {
        return Response.success(dailyTrainService.queryDailyStop(dailyTrainId, stopIndex));
    }
}
