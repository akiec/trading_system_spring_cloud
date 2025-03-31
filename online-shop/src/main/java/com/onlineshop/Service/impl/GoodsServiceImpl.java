package com.onlineshop.Service.impl;

import com.onlineshop.DTO.Result;
import com.onlineshop.Mapper.GoodsMapper;
import com.onlineshop.Service.GoodsService;
import com.onlineshop.entity.Goods;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private GoodsMapper goodsMapper;

    @Override
    //查询具体的商品信息
    public Result details(Long goodsId) {
        Goods goods = goodsMapper.details(goodsId);
        return Result.success(goods);
    }
}
