package com.onlineshop.Service;

import com.onlineshop.DTO.Result;

public interface OrderService {
    Result checkOrder(Long userId);
}
