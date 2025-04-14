package com.onlineshop.Mapper;

import com.onlineshop.entity.Payment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PaymentMapper {
    void createPayment(Payment payment);
    void deleteByOrderId(String orderId);
    Payment getDetailsById(String paymentId);
    void updateStatus(String paymentId, Integer status);
    List<Payment> toPayments(String userId);
}
