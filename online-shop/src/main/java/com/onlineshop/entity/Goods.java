package com.onlineshop.entity;

import cn.hutool.json.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    public String toString(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("goodsId", goodsId);
        jsonObject.put("name", name);
        jsonObject.put("description", description);
        jsonObject.put("price", price);
        jsonObject.put("stock", stock);
        jsonObject.put("image", image);
        jsonObject.put("category", category);
        jsonObject.put("createTime", createTime.toString());
        jsonObject.put("updateTime", updateTime.toString());
        jsonObject.put("version", version);
        jsonObject.put("productId", productId);
        return jsonObject.toString();
    }

}
