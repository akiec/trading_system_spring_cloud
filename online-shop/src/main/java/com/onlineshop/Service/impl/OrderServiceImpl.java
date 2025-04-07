package com.onlineshop.Service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.onlineshop.DTO.Result;
import com.onlineshop.Mapper.AddressMapper;
import com.onlineshop.Mapper.GoodsMapper;
import com.onlineshop.Mapper.OrderMapper;
import com.onlineshop.Mapper.UserMapper;
import com.onlineshop.Service.OrderService;
import com.onlineshop.Utils.IdCreater;
import com.onlineshop.Utils.NameContains;
import com.onlineshop.entity.Goods;
import com.onlineshop.entity.Order;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
//可选择利用线程池和消息队列进行轮询下单
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private IdCreater idCreater;
    @Resource
    private AddressMapper addressMapper;




    //查看用户的订单信息
    @Override
    public Result checkOrder(Long userId) {
        List<Order> orderList = orderMapper.checkOrder(userId);
        return Result.success(orderList, (long) orderList.size());
    }

    @Override
    public Result createOrder(Long userId, Long goodsId) {
        //前面具体页面使用了redis查询，所以一定存在
        String redisData = stringRedisTemplate.opsForValue().get(NameContains.Goods_NAME + goodsId);
        Goods goods = JSONUtil.toBean(redisData, Order.class, true);
        if (goods.getStock() < 1) {
            return Result.error("商品库存不足");
        }
        //数据库下单再修改redis缓存
        //乐观锁解决超卖
        Long version = idCreater.createId(NameContains.VERSION_NAME + goodsId);
        String newVersion = version.toString();
        Boolean isSuccess=goodsMapper.putOrder(goods,newVersion);
        if(isSuccess){
            //更新redis缓存
            Goods newGoods = goodsMapper.details(goodsId);
            stringRedisTemplate.opsForValue().set(NameContains.Goods_NAME + goodsId, newGoods.toString());
            //生成订单id
            Long orderId = idCreater.createId(NameContains.ORDER_ID + goodsId);
            //生成支付id
            Long paymentId = idCreater.createId(NameContains.PAYMENT_ID + orderId);
            //查找地址，地址维护表查询
            String address = addressMapper.searchByUserId(userId);


            Order order = new Order(orderId,userId,goods.getProductId(),paymentId,address,0,goodsId, LocalDateTime.now());
            orderMapper.createOrder(order);
        }

        return Result.success();
    }
}






