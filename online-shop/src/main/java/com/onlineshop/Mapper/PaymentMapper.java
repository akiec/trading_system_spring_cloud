package com.onlineshop.Mapper;

import com.onlineshop.entity.Payment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PaymentMapper {
    void createPayment(Payment payment);
    void deleteByOrderId(Long orderId);
    Payment getDetailsById(Long paymentId);
    void updateStatus(Long paymentId, Integer status);
    List<Payment> toPayments(Long userId);
}
