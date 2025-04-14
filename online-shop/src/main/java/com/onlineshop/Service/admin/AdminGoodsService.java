package com.onlineshop.Service.admin;

import com.onlineshop.DTO.Result;
import com.onlineshop.entity.Goods;

public interface AdminGoodsService {
    Result addGoods(Goods goods,String userId);

    Result delete(String goodsId);

    Result updateGoods(Goods goods);
}
