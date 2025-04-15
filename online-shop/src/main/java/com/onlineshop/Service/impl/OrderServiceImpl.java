package com.onlineshop.Service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.google.gson.Gson;
import com.onlineshop.DTO.Result;
import com.onlineshop.Mapper.*;
import com.onlineshop.Service.OrderService;
import com.onlineshop.Utils.IdCreater;
import com.onlineshop.Utils.NameContains;
import com.onlineshop.entity.Goods;
import com.onlineshop.entity.Order;
import com.onlineshop.entity.Payment;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
//可选择利用线程池和消息队列进行轮询下单
@Service
@Slf4j
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
    @Resource
    private PaymentMapper paymentMapper;

    //查看用户的订单信息
    @Override
    public Result checkOrder(String userId) {

        List<Order> orderList = orderMapper.checkOrder(userId);

        return Result.success(orderList, (long) orderList.size());
    }
    //创建订单
    @Override
    public Result createOrder(String userId, String goodsId,Integer count) {
        //前面具体页面使用了redis查询，所以一定存在
        String redisData = stringRedisTemplate.opsForValue().get(NameContains.Goods_NAME + goodsId);
        Goods goods =new Goods();
        if (StrUtil.isBlank(redisData)) {

       /* goods = goodsMapper.details(goodsId);
            log.info("查数据库"+goods.toString());*/
            Goods details = goodsMapper.details(goodsId);
            log.info("dnasuiodhnas"+details.toString());
        }else {
            log.info("查数据库"+redisData);
            goods = JSONUtil.toBean(redisData, Goods.class);
        }
        if (goods.getStock() < 1) {
            return Result.error("商品库存不足");
        }
        //数据库下单再修改redis缓存
        //乐观锁解决超卖
        Long version = idCreater.createId(NameContains.VERSION_NAME + goodsId);
        String newVersion = version.toString();
        Boolean isSuccess=goodsMapper.putOrder(goods.getGoodsId(),goods.getStock(),goods.getVersion(),newVersion);
        if(isSuccess){
            //更新redis缓存
            Goods newGoods = goodsMapper.details(goodsId);
            stringRedisTemplate.opsForValue().set(NameContains.Goods_NAME + goodsId, newGoods.toJsonString());
            //生成订单id
            String orderId = idCreater.createId(NameContains.ORDER_ID + goodsId).toString();
            //生成支付id
            String paymentId = idCreater.createId(NameContains.PAYMENT_ID + orderId).toString();
            //查找地址，地址维护表查询
            String address = addressMapper.searchByUserId(userId);
            //生成新的订单
            Order order = new Order(goods.getPrice()*count,count,orderId,userId,goods.getProductId(),paymentId,address,0,goodsId, LocalDateTime.now());
            orderMapper.createOrder(order);
            //生成待支付信息
            Payment payment = new Payment(order.getTotalPrice(),paymentId,orderId,userId,0,goods.getProductId(),LocalDateTime.now(),LocalDateTime.now());
            paymentMapper.createPayment(payment);
            return Result.success();
        }
        return Result.error("加载失败");


    }
    //删除订单
    @Override
    public Result deleteOrder(String orderId, Integer status) {
        //判断是否已经支付
        if(status>0){
            //删除订单
            orderMapper.delete(orderId);
            return Result.success("已经退款至您的账户");
        }
        //删除不需要的待支付信息
        paymentMapper.deleteByOrderId(orderId);
        orderMapper.delete(orderId);
        return Result.success("成功删除订单");
    }
    //查看具体订单信息
    @Override
    public Result details(String orderId) {
        return Result.success(orderMapper.getDetails(orderId));
    }


}






