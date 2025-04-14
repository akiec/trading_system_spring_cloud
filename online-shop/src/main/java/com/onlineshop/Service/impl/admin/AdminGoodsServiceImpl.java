package com.onlineshop.Service.impl.admin;

import cn.hutool.core.util.StrUtil;
import com.onlineshop.DTO.Result;
import com.onlineshop.Mapper.GoodsMapper;
import com.onlineshop.Service.admin.AdminGoodsService;
import com.onlineshop.Utils.IdCreater;
import com.onlineshop.Utils.NameContains;
import com.onlineshop.entity.Goods;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AdminGoodsServiceImpl implements AdminGoodsService {
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private IdCreater idCreater;
    //添加商品
    @Override
    public Result addGoods(Goods goods,Long userId) {
        Long id = idCreater.createId(NameContains.GOODS_ID + goods.getName());
        goods.setGoodsId(id);
        goods.setCreateTime(LocalDateTime.now());
        goods.setUpdateTime(LocalDateTime.now());
        goods.setProductId(userId);
        goodsMapper.addGoods(goods);
        return Result.success();
    }
    //删除商品
    @Override
    public Result delete(Long goodsId) {
        //查询Redis中是否存在
        String s = stringRedisTemplate.opsForValue().get(NameContains.Goods_NAME + goodsId);
        if(!StrUtil.isBlank(s)) {
            stringRedisTemplate.delete(NameContains.Goods_NAME + goodsId);
        }
        goodsMapper.delete(goodsId);
        return Result.success();
    }

    @Override
    public Result updateGoods(Goods goods) {
        goods.setUpdateTime(LocalDateTime.now());
        goodsMapper.updateGoods(goods);
        //查找Redis中是否存在
        String s = stringRedisTemplate.opsForValue().get(NameContains.Goods_NAME + goods.getGoodsId());
        if(!StrUtil.isBlank(s)) {
            stringRedisTemplate.opsForValue().set(NameContains.Goods_NAME + goods.getGoodsId(), goods.toString());
        }
        return Result.success();
    }
}
