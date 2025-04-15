package com.onlineshop.entity;

import cn.hutool.json.JSONObject;
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
public class Goods {
    //商品ID
    private String goodsId;
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
    private String productId;
    public String toString() {
        return "Goods{" +
                "goodsId='" + goodsId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", image='" + image + '\'' +
                ", category='" + category + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", version='" + version + '\'' +
                ", productId='" + productId + '\'' +
                '}';
    }
    public static Goods fromString(String str) {
        // 提取goodsId
        int start = str.indexOf("goodsId='") + "goodsId='".length();
        int end = str.indexOf("'", start);
        String goodsId = str.substring(start, end);

        // 提取name
        start = str.indexOf("name='") + "name='".length();
        end = str.indexOf("'", start);
        String name = str.substring(start, end);

        // 提取description
        start = str.indexOf("description='") + "description='".length();
        end = str.indexOf("'", start);
        String description = str.substring(start, end);

        // 提取price
        start = str.indexOf("price=") + "price=".length();
        end = str.indexOf(",", start);
        Double price = Double.parseDouble(str.substring(start, end));

        // 提取stock
        start = str.indexOf("stock=") + "stock=".length();
        end = str.indexOf(",", start);
        Integer stock = Integer.parseInt(str.substring(start, end));

        // 提取image
        start = str.indexOf("image='") + "image='".length();
        end = str.indexOf("'", start);
        String image = str.substring(start, end);

        // 提取category
        start = str.indexOf("category='") + "category='".length();
        end = str.indexOf("'", start);
        String category = str.substring(start, end);

        // 提取createTime
        start = str.indexOf("createTime=") + "createTime=".length();
        end = str.indexOf(",", start);
        LocalDateTime createTime = LocalDateTime.parse(str.substring(start, end));

        // 提取updateTime
        start = str.indexOf("updateTime=") + "updateTime=".length();
        end = str.indexOf(",", start);
        LocalDateTime updateTime = LocalDateTime.parse(str.substring(start, end));

        // 提取version
        start = str.indexOf("version='") + "version='".length();
        end = str.indexOf("'", start);
        String version = str.substring(start, end);

        // 提取productId
        start = str.indexOf("productId='") + "productId='".length();
        end = str.indexOf("'", start);
        String productId = str.substring(start, end);

        return new Goods(goodsId, name, description, price, stock, image, category, createTime, updateTime, version, productId);
    }
}
