package com.onlineshop.Service.impl;

import com.onlineshop.DTO.Result;
import com.onlineshop.Mapper.PaymentMapper;
import com.onlineshop.Service.PaymentService;
import com.onlineshop.entity.Payment;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Resource
    private PaymentMapper paymentMapper;

    @Override
    public Result details(Long paymentId) {
        Payment payment=paymentMapper.getDetailsById(paymentId);
        return Result.success(payment);
    }

    @Override
    public Result payOrder(Payment payment) {
        //暂时不知道怎么处理
        //收到款后设置
        Integer status=0;
        //未收到款
        if (true){
            status=1;
        }else {
            status=-1;
        }
        paymentMapper.updateStatus(payment.getPaymentId(),status);
        return Result.success();
    }

    @Override
    public Result toPayment(Long userId) {
        List<Payment> paymentList=paymentMapper.toPayments(userId);
        return Result.success(paymentList, Long.valueOf(paymentList.size()));
    }
}
