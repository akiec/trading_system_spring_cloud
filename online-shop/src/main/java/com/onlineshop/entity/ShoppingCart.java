package com.onlineshop.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart {
    //用户id
    @JsonSerialize(using = ToStringSerializer.class)
    private String userId;
    //商品信息
    private List<Goods> goodsList;
    //订单信息
    private List<Order> orderList;
}
