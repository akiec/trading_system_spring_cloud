package com.onlineshop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
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
}
