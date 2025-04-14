package com.onlineshop.Mapper;
import com.onlineshop.entity.Goods;
import com.onlineshop.entity.ShopCartGoods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShopCartMapper {

    List<Goods> searchByPage(String userId, int begin, int end);
    void delete(String goodsId);


    void add(ShopCartGoods shopCartGoods);
}
