package com.onlineshop.Service.impl;

import cn.hutool.json.JSONUtil;
import com.onlineshop.DTO.Result;
import com.onlineshop.Mapper.*;
import com.onlineshop.Service.ShopCartService;
import com.onlineshop.Utils.IdCreater;
import com.onlineshop.Utils.NameContains;
import com.onlineshop.entity.*;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShopCartServiceImpl implements ShopCartService {
    @Resource
    private ShopCartMapper shopCartMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private IdCreater idCreater;
    @Resource
    private AddressMapper addressMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private PaymentMapper paymentMapper;
    //查看购物车商品
    @Override
    public Result searchByPage(String userId, int page, int pageSize) {
        int begin = (page - 1) * pageSize;
        int end = page * pageSize;
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setGoodsList(shopCartMapper.searchByPage(userId,begin,end));
        return Result.success(shoppingCart);
    }
    //删除购物车
    @Override
    public Result delete(String goodsId) {
        shopCartMapper.delete(goodsId);
        return null;
    }
    //结算
    @Override
    public Result summary(String userId, List<Goods> goodsList) {
        List<Payment> paymentList = new ArrayList<>(goodsList.size());
        for (Goods goods : goodsList) {
            if(goods.getStock()<=0){
                return Result.error(goods.getName()+"库存不足");
            }
            //生成订单id
            String orderId = idCreater.createId(NameContains.ORDER_ID + goods.getGoodsId()).toString();
            //生成支付id
            String paymentId = idCreater.createId(NameContains.PAYMENT_ID + orderId).toString();
            //查找地址，地址维护表查询
            String address = addressMapper.searchByUserId(userId);
            //生成新的订单
            Order order = new Order(goods.getPrice(),1,orderId,userId,goods.getProductId(),paymentId,address,0,goods.getGoodsId(), LocalDateTime.now());
            orderMapper.createOrder(order);
            //生成待支付信息
            Payment payment = new Payment(order.getTotalPrice(),paymentId,orderId,userId,0,goods.getProductId(),LocalDateTime.now(),LocalDateTime.now());
            paymentMapper.createPayment(payment);
            paymentList.add(payment);
        }
        return Result.success(paymentList, (long) paymentList.size());
    }

    @Override
    public Result add(Goods goods,String userId) {
        goods.setCreateTime(LocalDateTime.now());
        goods.setUpdateTime(LocalDateTime.now());
        ShopCartGoods shopCartGoods = new ShopCartGoods(goods.getGoodsId(),goods.getName(),goods.getDescription(),goods.getPrice(),goods.getStock(),goods.getImage(),goods.getCategory(),goods.getCreateTime(),goods.getUpdateTime(),goods.getVersion(),goods.getProductId(),userId);
        shopCartMapper.add(shopCartGoods);
        return Result.success();
    }
}
