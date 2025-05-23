package com.onlineshop.controller;

import com.onlineshop.DTO.Result;
import com.onlineshop.Service.OrderService;
import com.onlineshop.Utils.IdCreater;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderService orderService;

    //下单
    @PostMapping("/createOrder/{userId}/{goodsId}/{count}")
    public Result createOrder(@PathVariable String userId, @PathVariable String goodsId,@PathVariable Integer count){
        return orderService.createOrder(userId,goodsId,count);
    }
    //查看某一用户订单
    @GetMapping("/check/{id}")
    public Result checkOrder(@PathVariable ("id") String userId){
        return orderService.checkOrder(userId);
    }
    //查看某一具体订单信息
    @GetMapping("/details/{id}")
    public Result details(@PathVariable ("id") String orderId){
        return orderService.details(orderId);
    }
    //修改订单信息

    //取消订单or退款？
    @DeleteMapping("/delete/{id}/{status}")
    public Result deleteOrder(@PathVariable ("id") String orderId,@PathVariable("status") Integer status ){
        return orderService.deleteOrder(orderId,status);
    }

}
