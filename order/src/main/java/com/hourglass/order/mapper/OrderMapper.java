package com.hourglass.order.mapper;

import com.hourglass.order.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    Integer insert(Order order);

    Integer updateStateByOrderId(Long orderId, Integer orderState);
}
