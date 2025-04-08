package com.onlineshop.Service;

import com.onlineshop.DTO.Result;

public interface OrderService {
    Result checkOrder(Long userId);

    Result createOrder(Long userId, Long goodsId,Integer count);

    Result deleteOrder(Long orderId,Integer status);

    Result details(Long orderId);
}
