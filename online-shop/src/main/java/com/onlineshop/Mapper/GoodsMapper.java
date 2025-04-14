package com.onlineshop.Mapper;

import com.onlineshop.entity.Goods;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsMapper {

    Goods details(String goodsId);

    List<Goods> searchByPage(int begin, int end);

    List<Goods> searchByType(String type, int begin, int end);

    List<Goods> searchByName(String name, int begin, int end);

    void addGoods(Goods goods);

    void delete(String goodsId);

    void updateGoods(Goods goods);

    Boolean putOrder(Goods goods,String newVersion);

    List<Goods> searchByProduct(String productId);
}
