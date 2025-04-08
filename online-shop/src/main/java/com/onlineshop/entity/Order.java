package com.onlineshop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    //订单金额
    private double totalPrice;
    //商品数量
    private int count;
    //订单id
    private Long orderId;
    //客户id
    private Long customerId;
    //商户id
    private Long productId;
    //支付链接id
    private Long paymentId;
    //客户地址
    private String address;
    //订单状态
    private Integer status;
    //商品id
    private Long goodsId;
    //下单时间
    private LocalDateTime orderTime;
}
