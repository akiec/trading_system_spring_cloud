package com.onlineshop.Service;

import com.onlineshop.DTO.Result;
import com.onlineshop.entity.Goods;

import java.util.List;

public interface ShopCartService {
    Result searchByPage(String userId, int page, int pageSize);

    Result delete(String goodsId);

    Result summary(String userId, List<Goods> goodsList);

    Result add(Goods goods,String userId);
}
