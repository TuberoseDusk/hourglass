package com.hourglass.schedule.controller;

import com.hourglass.common.response.Response;
import com.hourglass.schedule.request.CarriageAddRequest;
import com.hourglass.schedule.request.CarriageCreateRequest;
import com.hourglass.schedule.request.StopAddRequest;
import com.hourglass.schedule.request.TrainCreateRequest;
import com.hourglass.schedule.response.CarriageQueryResponse;
import com.hourglass.schedule.service.CarriageService;
import com.hourglass.schedule.service.StopService;
import com.hourglass.schedule.service.TrainService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/train")
public class TrainAdminController {
    @Resource
    private CarriageService carriageService;

    @Resource
    private TrainService trainService;

    @Resource
    private StopService stopService;

    /**
     * 创建车厢类型
     */
    @PostMapping("/createCarriage")
    public Response<Void> createCarriage(@RequestBody CarriageCreateRequest carriageCreateRequest) {
        carriageService.create(carriageCreateRequest);
        return Response.success();
    }

    /**
     * 按类型号码查询车厢类型
     */
    @GetMapping("/queryCarriage")
    public Response<CarriageQueryResponse> query(@RequestParam Integer carriageType) {
        return Response.success(carriageService.query(carriageType));
    }

    /**
     * 创建列车数据
     */
    @PostMapping("/createTrain")
    public Response<Void> createTrain(@Valid @RequestBody TrainCreateRequest trainCreateRequest) {
        trainService.create(trainCreateRequest);
        return Response.success();
    }

    /**
     * 添加车站信息
     */
    @PutMapping("/addStop")
    public Response<Void> addStop(@Valid @RequestBody StopAddRequest stopAddRequest) {
        stopService.add(stopAddRequest);
        return Response.success();
    }

    /**
     * 添加车厢
     */
    @PutMapping("/addCarriage")
    public Response<Void> addCarriage(@Valid @RequestBody CarriageAddRequest carriageAddRequest) {
        trainService.addCarriage(carriageAddRequest);
        return Response.success();
    }

}
