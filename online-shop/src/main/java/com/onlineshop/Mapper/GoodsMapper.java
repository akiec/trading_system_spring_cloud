package com.onlineshop.Mapper;

import com.onlineshop.entity.Goods;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsMapper {

    Goods details(Long goodsId);
}
