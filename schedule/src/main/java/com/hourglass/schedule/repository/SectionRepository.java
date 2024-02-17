package com.hourglass.schedule.repository;

import com.hourglass.common.enums.RedisPrefixEnum;
import com.hourglass.schedule.response.SectionQueryResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Repository
@Slf4j
public class SectionRepository {
    @Resource
    private RedissonClient redissonClient;

    public void put(LocalDate date, String startStation, String endStation,
                    List<SectionQueryResponse> sectionQueryResponses) {
        String key = RedisPrefixEnum.SECTION_CACHE.getPrefix() + date + "-" + startStation + "-" + endStation;
        RBucket<List<SectionQueryResponse>> bucket = redissonClient.getBucket(key);
        bucket.set(sectionQueryResponses, Duration.ofMinutes(45));
        log.info("乘车线路已缓存：{}  {} -> {}", date, startStation, endStation);
    }

    public List<SectionQueryResponse> get(LocalDate date, String startStation, String endStation) {
        String key = RedisPrefixEnum.SECTION_CACHE.getPrefix() + date + "-" + startStation + "-" + endStation;
        RBucket<List<SectionQueryResponse>> bucket = redissonClient.getBucket(key);
        if (bucket.isExists()) {
            log.info("命中乘车线路缓存：{}  {} -> {}", date, startStation, endStation);
            return bucket.get();
        }
        log.info("未命中乘车线路缓存：{}  {} -> {}", date, startStation, endStation);
        return null;
    }
}
