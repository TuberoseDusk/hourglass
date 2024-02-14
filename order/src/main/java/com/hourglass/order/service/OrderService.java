package com.hourglass.order.service;

import com.hourglass.common.enums.MQEnum;
import com.hourglass.common.enums.ResponseEnum;
import com.hourglass.common.exception.BusinessException;
import com.hourglass.common.util.Snowflake;
import com.hourglass.order.entity.Order;
import com.hourglass.order.enums.OrderStateEnum;
import com.hourglass.order.enums.RedisPrefixEnum;
import com.hourglass.order.mapper.OrderMapper;
import com.hourglass.order.request.OrderSeizeRequest;
import com.hourglass.order.message.TicketMessage;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class OrderService {
    @Resource
    private OrderMapper orderMapper;

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private TicketService ticketService;

    @Resource
    private RocketMQTemplate rocketMQTemplate;


    public void seize(OrderSeizeRequest orderSeizeRequest) {

        // 获取坐席信息
        Map<String, Integer> availableTickets = ticketService.queryTicket(orderSeizeRequest.getDailyTrainId(),
                orderSeizeRequest.getStartStopIndex(),
                orderSeizeRequest.getEndStopIndex());
        Integer tickets = availableTickets.get(orderSeizeRequest.getSeatTypeMark());
        if (tickets == null) {
            // 不提供该坐席
            throw new BusinessException(ResponseEnum.SEAT_TYPE_NO_PROVIDE);
        }
        if (tickets <= 0) {
            // 售空
            throw new BusinessException(ResponseEnum.SEAT_SOLD_OUT);
        }

        // 分布式锁 key prefix:{dailyTrainId}-{seatType}
        String lockKey = RedisPrefixEnum.ORDER_LOCK.getPrefix() + orderSeizeRequest.getDailyTrainId() + "-" + orderSeizeRequest.getSeatTypeMark();
        RLock lock = redissonClient.getLock(lockKey);

        if (!lock.tryLock()) {
            // 获取锁失败
            throw new BusinessException(ResponseEnum.ORDER_LOCK_ERROR);
        }

        // 分配坐席
        Integer seatNumber = ticketService.decreaseTicket(orderSeizeRequest.getDailyTrainId(),
                                                        orderSeizeRequest.getStartStopIndex(),
                                                        orderSeizeRequest.getEndStopIndex(),
                                                        orderSeizeRequest.getSeatTypeMark());
        lock.unlock();  // 扣库存完成，释放锁

        if (seatNumber == null) {
            // 扣库存失败
            throw new BusinessException(ResponseEnum.SEAT_ASSIGN_ERROR);
        }
        log.info("分配席位\t车次：{}\t座位：{}", orderSeizeRequest.getDailyTrainId(), seatNumber);

        // 插入订单表
        Order order = new Order(Snowflake.nextId(), orderSeizeRequest.getUserId(), OrderStateEnum.NO_ISSUE.getState());
        log.info("生成订单{}", order);
        orderMapper.insert(order);

        //MQ
        TicketMessage ticketResponse = new TicketMessage(orderSeizeRequest.getUserId(), orderSeizeRequest.getDailyTrainId(), seatNumber,
                orderSeizeRequest.getStartStopIndex(), orderSeizeRequest.getEndStopIndex(), order.getOrderId());
        rocketMQTemplate.convertAndSend(MQEnum.ORDER_CONFIRM.getTopic(), ticketResponse);
        log.info("向{}投递消息{}", MQEnum.ORDER_CONFIRM.getTopic(), ticketResponse);
    }
}
