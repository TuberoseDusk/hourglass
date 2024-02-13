package com.hourglass.order.repository;

import com.hourglass.order.enums.RedisPrefixEnum;
import jakarta.annotation.Resource;
import org.redisson.api.RBitSet;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Repository;

import java.util.BitSet;

/**
 * 管理 Redis 中的余票库存
 * key {dailyTrainId}-{stopIndex}
 * value bitmap
 */
@Repository
public class TicketRepository {
    @Resource
    private RedissonClient redissonClient;

    /**
     * 根据车次编号和停站序号向Redis中插入座位信息
     * @param dailyTrainId 车次id
     * @param stopIndex 停站序号
     */
    public void insertTicketOfDailyTrainAtStation(Long dailyTrainId, Integer stopIndex, BitSet tickets) {
        String key = RedisPrefixEnum.TICKET_STATE.getPrefix() + dailyTrainId + "-" + stopIndex;
        RBitSet bitSet = redissonClient.getBitSet(key);
        bitSet.set(tickets);
    }

    /**
     *查询座位信息
     */

    public BitSet selectTicketOfDailyTrainAtStation(Long dailyTrainId, Integer stopIndex) {
        String key = RedisPrefixEnum.TICKET_STATE.getPrefix() + dailyTrainId + "-" + stopIndex;
        RBitSet bitSet = redissonClient.getBitSet(key);
        if (!bitSet.isExists()) {
            return null;
        }
        return BitSet.valueOf(bitSet.toByteArray());
    }
}
