package com.onlineshop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart {
    //用户id
    private Long userId;
    //商品信息
    private List<Goods> goodsList;
    //订单信息
    private List<Order> orderList;
}
