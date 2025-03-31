package com.onlineshop.Utils;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static com.onlineshop.Utils.NameContains.BEGIN_TIME;
import static com.onlineshop.Utils.NameContains.COUNT;

@Component

//id生成器直接调用即可
public class IdCreater {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    //传递一个前缀生成拼接id
    public Long createId(String keyPrefix) {
        //生成时间戳
        LocalDateTime now = LocalDateTime.now();
        long nowSecond = now.toEpochSecond(ZoneOffset.UTC);
        long stampTime = nowSecond-BEGIN_TIME;
        //生成序列号
        //获取当前日期
        String data = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Long count = stringRedisTemplate.opsForValue().increment("icr" + keyPrefix + ":" + data);
        //位运算生成id
        return stampTime<<COUNT | count;
    }


}
