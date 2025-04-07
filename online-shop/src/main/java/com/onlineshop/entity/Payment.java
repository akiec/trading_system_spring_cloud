package com.onlineshop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    //支付id
    private Long paymentId;
    //订单id
    private Long orderId;
    //用户id
    private Long userId;
    //支付状态（0->未支付，1->支付成功，-1->支付失败）
    private Integer paymentStatus;
    //商户id
    private Long productId;
    //创建时间
    LocalDateTime createTime;
    //修改时间
    LocalDateTime updateTime;
}
