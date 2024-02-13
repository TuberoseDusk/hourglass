package com.hourglass.order.feign;

import com.hourglass.common.response.Response;
import com.hourglass.order.response.DailyTrainQueryResponse;
import com.hourglass.order.response.SeatQueryResponse;
import com.hourglass.order.response.SectionQueryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "schedule", path = "/schedule")
public interface ScheduleFeignService {

    @GetMapping("/daily/train/querySection/{startStation}/{endStation}/{date}")
    Response<List<SectionQueryResponse>> querySection(@PathVariable String startStation,
                                                      @PathVariable String endStation,
                                                      @PathVariable(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date);

    @GetMapping("/daily/train/queryDailyTrain/{date}")
    Response<List<DailyTrainQueryResponse>> queryDailyTrain(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date);

    @GetMapping("/seat/querySeat/{dailyTrainId}")
    Response<List<SeatQueryResponse>> querySeat(@PathVariable Long dailyTrainId);
}
