package com.hourglass.ticket.mapper;

import com.hourglass.ticket.entity.Ticket;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TicketMapper {
    Integer insert(Ticket ticket);
}
