package com.onlineshop.controller;

import com.onlineshop.DTO.Result;
import com.onlineshop.Service.PaymentService;
import com.onlineshop.Utils.UserHolder;
import com.onlineshop.entity.Payment;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/payment")
public class PaymentController {
    @Resource
    PaymentService paymentService;
    //查看支付信息
    @GetMapping("/details/{id}")
    public Result details(@PathVariable ("id") Long paymentId) {
        return paymentService.details(paymentId);
    }
    //支付
    @PostMapping("/payOrder")
    public Result payOrder(@RequestBody Payment payment) {
        return paymentService.payOrder(payment);
    }
    //购物车支付
    @PostMapping("/payShopCart")
    public Result payShopCart(@RequestBody List<Payment> payments) {
        return paymentService.PayShopCart(payments);
    }
}
