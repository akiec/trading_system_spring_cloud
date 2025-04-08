package com.onlineshop.Service;

import com.onlineshop.DTO.Result;
import com.onlineshop.entity.Goods;

import java.util.List;

public interface ShopCartService {
    Result searchByPage(Long userId, int page, int pageSize);

    Result delete(Long goodsId);

    Result summary(Long userId, List<Goods> goodsList);
}
