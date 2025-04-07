package com.onlineshop.controller;

import com.onlineshop.DTO.Result;
import com.onlineshop.Service.OrderService;
import com.onlineshop.Utils.IdCreater;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderService orderService;

    //下单
    @PostMapping("/createOrder/{userId}/{goodsId}")
    public Result createOrder(@PathVariable Long userId, @PathVariable Long goodsId){
        return orderService.createOrder(userId,goodsId);
    }
    //查看订单
    @GetMapping("/check/{id}")
    public Result checkOrder(@PathVariable ("id") Long userId){
        return orderService.checkOrder(userId);
    }

}
