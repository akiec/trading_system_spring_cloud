package com.onlineshop.Mapper;

import com.onlineshop.entity.Order;

import java.util.List;

public interface OrderMapper {
    List<Order> checkOrder(Long userId);
}
