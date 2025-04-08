package com.onlineshop.Service;

import com.onlineshop.DTO.Result;
import com.onlineshop.entity.Payment;

import java.util.List;

public interface PaymentService {

    Result details(Long paymentId);

    Result payOrder(Payment payment);

    Result toPayment(Long userId);

    Result PayShopCart(List<Payment> payments);
}
