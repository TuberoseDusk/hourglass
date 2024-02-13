package com.hourglass.order.controller;

import com.hourglass.common.response.Response;
import com.hourglass.order.feign.ScheduleFeignService;
import com.hourglass.order.request.TicketGenerateRequest;
import com.hourglass.order.response.SectionQueryResponse;
import com.hourglass.order.response.TicketQueryResponse;
import com.hourglass.order.service.TicketService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Resource
    private ScheduleFeignService scheduleFeignService;

    @Resource
    private TicketService ticketService;

    /**
     *按发车日期生成车票
     */
    @PostMapping("/generateTicket")
    public Response<Void> generateTicket(@Valid @RequestBody TicketGenerateRequest ticketGenerateRequest) {
        ticketService.generateAll(ticketGenerateRequest);
        return Response.success();
    }

    /**
     *查询乘车线路
     */
    @GetMapping("/querySection/{startStation}/{endStation}/{date}")
    public Response<List<TicketQueryResponse>> querySection(@PathVariable String startStation,
                                                             @PathVariable String endStation,
                                                             @PathVariable(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        List<TicketQueryResponse> ticketQueryResponses = ticketService.query(startStation, endStation, date);
        return Response.success(ticketQueryResponses);
    }


}
