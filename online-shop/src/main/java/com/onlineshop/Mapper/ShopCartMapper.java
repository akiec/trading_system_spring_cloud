package com.onlineshop.Mapper;
import com.onlineshop.entity.Goods;
import com.onlineshop.entity.ShopCartGoods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShopCartMapper {

    List<Goods> searchByPage(Long userId, int begin, int end);
    void delete(Long goodsId);


    void add(ShopCartGoods shopCartGoods);
}
