package com.onlineshop.entity;

import cn.hutool.json.JSONObject;
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
public class Goods {
    //商品ID
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
    private Long productId;
    public static Goods fromString(String str) {
        Goods goods = new Goods();
        Map<String, String> paramMap = new HashMap<>();
        String[] pairs = str.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                paramMap.put(keyValue[0], keyValue[1]);
            }
        }

        if (paramMap.containsKey("goodsId")) {
            goods.goodsId = Long.parseLong(paramMap.get("goodsId"));
        }
        if (paramMap.containsKey("name")) {
            goods.name = paramMap.get("name");
        }
        if (paramMap.containsKey("description")) {
            goods.description = paramMap.get("description");
        }
        if (paramMap.containsKey("price")) {
            goods.price = Double.parseDouble(paramMap.get("price"));
        }
        if (paramMap.containsKey("stock")) {
            goods.stock = Integer.parseInt(paramMap.get("stock"));
        }
        if (paramMap.containsKey("image")) {
            goods.image = paramMap.get("image");
        }
        if (paramMap.containsKey("category")) {
            goods.category = paramMap.get("category");
        }
        if (paramMap.containsKey("createTime")) {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            goods.createTime = LocalDateTime.parse(paramMap.get("createTime"), formatter);
        }
        if (paramMap.containsKey("updateTime")) {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            goods.updateTime = LocalDateTime.parse(paramMap.get("updateTime"), formatter);
        }
        if (paramMap.containsKey("version")) {
            goods.version = paramMap.get("version");
        }
        if (paramMap.containsKey("productId")) {
            goods.productId = Long.parseLong(paramMap.get("productId"));
        }

        return goods;
    }

}
