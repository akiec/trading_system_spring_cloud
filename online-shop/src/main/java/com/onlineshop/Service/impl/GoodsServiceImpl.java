package com.onlineshop.Service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.onlineshop.DTO.Result;
import com.onlineshop.Mapper.GoodsMapper;
import com.onlineshop.Service.GoodsService;
import com.onlineshop.Utils.NameContains;
import com.onlineshop.entity.Goods;
import jakarta.annotation.Resource;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Service;
import com.google.gson.*;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private GoodsMapper goodsMapper;

    @Override
    //查询具体的商品信息
    //加入缓存
    public Result details(String goodsId) {
        //查找缓存中是否存在
        String redisGoods = stringRedisTemplate.opsForValue().get(NameContains.Goods_NAME + goodsId);
        //不存在查找并写入
        if(StrUtil.isBlank(redisGoods)){
            Goods newgoods = goodsMapper.details(goodsId);
            stringRedisTemplate.opsForValue().set(NameContains.Goods_NAME + goodsId, newgoods.toString());
            return Result.success(newgoods);
        }
        //存在直接返回
        Goods goods = JSONUtil.toBean(redisGoods, Goods.class);
        return Result.success(goods);
    }
    @Override
    public Result searchByPage(int page,int pageSize) {
        //起始页
        int begin = (page - 1) * pageSize;
        //终点
        int end = begin + pageSize;
        //查询
        List<Goods> goodsList = goodsMapper.searchByPage(begin,end);
        return Result.success(goodsList, (long) pageSize);
    }

    @Override
    public Result searchByType(String type, int page, int pageSize) {
        //起始页
        int begin = (page - 1) * pageSize;
        //终点
        int end = begin + pageSize;
        //查询
        List<Goods> goodsList = goodsMapper.searchByType(type,begin,end);
        return Result.success(goodsList, (long) pageSize);
    }

    @Override
    public Result searchByName(String name, int page, int pageSize) {
        //起始页
        int begin = (page - 1) * pageSize;
        //终点
        int end = begin + pageSize;
        //查询
        List<Goods> goodsList = goodsMapper.searchByName(name,begin,end);
        return Result.success(goodsList, (long) pageSize);
    }
//根据厂家查
    @Override
    public Result searchByroduct(String productId) {
        List<Goods> goodsList = goodsMapper.searchByProduct(productId);
        return Result.success(goodsList);
    }
}
