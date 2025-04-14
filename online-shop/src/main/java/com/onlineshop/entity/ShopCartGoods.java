package com.onlineshop.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopCartGoods {
    //商品ID
    @JsonSerialize(using = ToStringSerializer.class)
    private Long goodsId;
    //商品名称
    private String name;
    //商品描述
    private String description;
    //商品价格
    private Double price;
    //商品库存
    private Integer stock;
    //商品图片
    private String image;
    //商品类别
    private String category;
    //创建时间
    private LocalDateTime createTime;
    //更新时间
    private LocalDateTime updateTime;
    //版本号
    private String version;
    //商户ID
    @JsonSerialize(using = ToStringSerializer.class)
    private Long productId;
    //用户id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
}
