package com.onlineshop.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    //支付金额
    private double totalMoney;
    //支付id
    @JsonSerialize(using = ToStringSerializer.class)
    private String paymentId;
    //订单id
    @JsonSerialize(using = ToStringSerializer.class)
    private String orderId;
    //用户id
    @JsonSerialize(using = ToStringSerializer.class)
    private String userId;
    //支付状态（0->未支付，1->支付成功，-1->支付失败）
    private Integer paymentStatus;
    //商户id
    @JsonSerialize(using = ToStringSerializer.class)
    private String productId;
    //创建时间
    LocalDateTime createTime;
    //修改时间
    LocalDateTime updateTime;
}
