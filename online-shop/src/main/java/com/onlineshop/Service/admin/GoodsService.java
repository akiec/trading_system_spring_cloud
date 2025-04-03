package com.onlineshop.Service.admin;

import com.onlineshop.DTO.Result;
import com.onlineshop.entity.Goods;

public interface GoodsService {
    Result addGoods(Goods goods);

    Result delete(Long goodsId);

    Result updateGoods(Goods goods);
}
