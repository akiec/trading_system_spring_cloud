package com.onlineshop.Service.impl;

import com.onlineshop.DTO.Result;
import com.onlineshop.Mapper.OrderMapper;
import com.onlineshop.Service.OrderService;
import com.onlineshop.entity.Order;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private OrderMapper orderMapper;




    //查看用户的订单信息
    @Override
    public Result checkOrder(Long userId) {
        List<Order> orderList = orderMapper.checkOrder(userId);
        return Result.success(orderList, (long) orderList.size());
    }
}
