package com.hourglass.order.controller;

import com.hourglass.common.response.Response;
import com.hourglass.order.request.OrderSeizeRequest;
import com.hourglass.order.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderService orderService;

    /**
     * 抢票接口
     */
    @PostMapping("/seize")
    public Response<Void> seize(@RequestBody OrderSeizeRequest orderSeizeRequest) {
        orderService.seize(orderSeizeRequest);
        return Response.success();
    }
}
