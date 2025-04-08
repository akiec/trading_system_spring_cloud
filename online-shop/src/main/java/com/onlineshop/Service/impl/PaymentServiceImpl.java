package com.onlineshop.Service.impl;

import com.onlineshop.DTO.Result;
import com.onlineshop.Mapper.OrderMapper;
import com.onlineshop.Mapper.PaymentMapper;
import com.onlineshop.Mapper.ShopCartMapper;
import com.onlineshop.Service.PaymentService;
import com.onlineshop.entity.Order;
import com.onlineshop.entity.Payment;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Resource
    private PaymentMapper paymentMapper;
    @Resource
    private ShopCartMapper shopCartMapper;
    @Resource
    private OrderMapper orderMapper;

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
        //查看是否在购物车中

        return Result.success();
    }

    @Override
    public Result toPayment(Long userId) {
        List<Payment> paymentList=paymentMapper.toPayments(userId);
        return Result.success(paymentList, Long.valueOf(paymentList.size()));
    }
    //购物车支付实现
    @Override
    public Result PayShopCart(List<Payment> payments) {
        Integer status=0;
        for (Payment payment : payments) {
            //未收到款
            if (true){
                status=1;
            }else {
                status=-1;
            }
            paymentMapper.updateStatus(payment.getPaymentId(),status);
            Order temOrder = orderMapper.getDetails(payment.getOrderId());
            shopCartMapper.delete(temOrder.getGoodsId());
        }
        return Result.success();
    }
}
