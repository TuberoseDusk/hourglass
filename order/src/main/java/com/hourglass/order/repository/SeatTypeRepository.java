package com.hourglass.order.repository;

import com.hourglass.common.enums.SeatTypeEnum;
import com.hourglass.order.enums.RedisPrefixEnum;
import jakarta.annotation.Resource;
import org.redisson.api.RBitSet;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Repository;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理 Redis 中的座位类型信息
 * key {dailyTrainId}-{seatType}
 * value bitmap
 */
@Repository
public class SeatTypeRepository {

    public static final Map<Integer, String> seatTypeMap = new HashMap<>();
    static {
         for (SeatTypeEnum seatTypeEnum : SeatTypeEnum.values()) {
             seatTypeMap.put(seatTypeEnum.getType(), seatTypeEnum.getMsg());
         }
    }

    @Resource
    private RedissonClient redissonClient;

    public void insertSeatTypeMask(Long dailyTrainId, Integer seatType, BitSet seatMask) {
        String key = RedisPrefixEnum.SEAT_MASK.getPrefix() + dailyTrainId + "-" + seatTypeMap.get(seatType);
        RBitSet bitSet = redissonClient.getBitSet(key);
        bitSet.set(seatMask);
    }

    public BitSet selectSeatTypeMask(Long dailyTrainId, Integer seatType) {
        String key = RedisPrefixEnum.SEAT_MASK.getPrefix() + dailyTrainId + "-" + seatTypeMap.get(seatType);
        RBitSet bitSet = redissonClient.getBitSet(key);
        if (!bitSet.isExists()) {
            return null;
        }
        return BitSet.valueOf(bitSet.toByteArray());
    }
}
