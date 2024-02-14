package com.hourglass.ticket.feign;

import com.hourglass.common.response.Response;
import com.hourglass.ticket.request.SeatStateUpdateRequest;
import com.hourglass.ticket.response.DailyStopResponse;
import com.hourglass.ticket.response.DailyTrainQueryResponse;
import com.hourglass.ticket.response.SeatQueryResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "schedule", path = "/schedule")
public interface ScheduleFeignService {
    // 根据dailyTrainId查询dailyTrain信息
    @GetMapping("/daily/train/queryDailyTrainById/{dailyTrainId}")
    Response<DailyTrainQueryResponse> queryDailyTrainById(@PathVariable Long dailyTrainId);

    // 根据dailyTrainId和seatNumber查询seat信息
    @GetMapping("/seat/querySeatSingle/{dailyTrainId}/{seatNumber}")
    Response<SeatQueryResponse> querySeatSingle(@PathVariable Long dailyTrainId, @PathVariable Integer seatNumber);

    // 根据dailyTrainId和stopIndex查询dailyStop信息
    @GetMapping("/daily/train/queryDailyStop/{dailyTrainId}/{stopIndex}")
    Response<DailyStopResponse> queryDailyStop(@PathVariable Long dailyTrainId, @PathVariable Integer stopIndex);

    // 修改seat的state字段
    @PutMapping("/seat/updateState")
    Response<Void> updateState(@Valid @RequestBody SeatStateUpdateRequest seatStateUpdateRequest);
}
