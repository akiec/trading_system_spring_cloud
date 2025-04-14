package com.onlineshop.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderId;
    //客户id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long customerId;
    //商户id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long productId;
    //支付链接id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long paymentId;
    //客户地址
    private String address;
    //订单状态
    private Integer status;
    //商品id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long goodsId;
    //下单时间
    private LocalDateTime orderTime;
}
