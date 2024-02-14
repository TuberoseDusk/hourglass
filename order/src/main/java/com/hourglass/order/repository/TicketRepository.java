package com.hourglass.order.repository;

import com.hourglass.order.enums.RedisPrefixEnum;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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

        // redis的BitMap二进制存储结构从左往右
        // java的BitSet二进制存储结构从右往左
        byte[] bytes = bitSet.toByteArray();
        BitSet bits = new BitSet();
        for (int i = 0; i < bytes.length * 8; i++) {
            if ((bytes[i / 8] & (1 << (7 - (i % 8)))) != 0) {
                bits.set(i);
            }
        }
        log.info("查询车次{}第{}站状态：{}", dailyTrainId, stopIndex, bits);
        return bits;
    }

    /**
     *占用座位
     */
    public void occupyTicketOfDailyTrainAtStation(Long dailyTrainId, Integer stopIndex, Integer seatNumber) {
        String key = RedisPrefixEnum.TICKET_STATE.getPrefix() + dailyTrainId + "-" + stopIndex;
        RBitSet bitSet = redissonClient.getBitSet(key);
        if (bitSet.clear(seatNumber)) {
            log.info("占用车次{}第{}站第{}号座位", dailyTrainId, stopIndex, seatNumber);
        } else {
            log.info("车次{}第{}站第{}号座位占用失败", dailyTrainId, stopIndex, seatNumber);
        }

    }

    /**
     *释放座位
     */
    public void releaseTicketOfDailyTrainAtStation(Long dailyTrainId, Integer stopIndex, Integer seatNumber) {
        String key = RedisPrefixEnum.TICKET_STATE.getPrefix() + dailyTrainId + "-" + stopIndex;
        RBitSet bitSet = redissonClient.getBitSet(key);
        bitSet.set(seatNumber);
    }


}
