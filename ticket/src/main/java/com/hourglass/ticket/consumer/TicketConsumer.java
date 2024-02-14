package com.hourglass.ticket.consumer;


import com.hourglass.ticket.message.TicketMessage;
import com.hourglass.ticket.service.TicketService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "ORDER_CONFIRM", consumerGroup = "default")
@Slf4j
public class TicketConsumer implements RocketMQListener<TicketMessage> {

    @Resource
    private TicketService ticketService;

    @Override
    public void onMessage(TicketMessage ticketMessage) {
        log.info("处理TicketMessage: {}", ticketMessage.toString());
        ticketService.create(ticketMessage);
    }
}
