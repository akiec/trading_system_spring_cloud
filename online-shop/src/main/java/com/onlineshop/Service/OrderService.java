package com.onlineshop.Service;

import com.onlineshop.DTO.Result;

public interface OrderService {
    Result checkOrder(String userId);

    Result createOrder(String userId, String goodsId,Integer count);

    Result deleteOrder(String orderId,Integer status);

    Result details(String orderId);
}
