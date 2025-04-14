package com.onlineshop.controller;

import com.onlineshop.DTO.Result;
import com.onlineshop.Service.ShopCartService;
import com.onlineshop.entity.Goods;
import jakarta.annotation.Resource;
import org.springframework.data.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/shopCart")
public class ShopCartController {
    @Resource
    private ShopCartService shopCartService;
    //查看购物车信息
    //分页查询吧
    @PostMapping("")
    public Result searchByPage(@RequestParam Long userId,@RequestParam(value = "page",defaultValue = "1") int page, @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
        return shopCartService.searchByPage(userId,page,pageSize);
    }
    //结算订单(多个或一个)
    @PostMapping("/summary")
    public Result summary(@RequestParam Long userId, @RequestParam List<Goods> goodsList){
        return shopCartService.summary(userId,goodsList);
    }
    //删除某一商品
    @DeleteMapping("/delete/{goodsId}")
    public Result delete(@PathVariable("goodsId") Long goodsId){
        return shopCartService.delete(goodsId);
    }
    //加购物车
    @PostMapping("/add")
    public Result add(@RequestBody Goods goods,@RequestParam Long userId){
        return shopCartService.add(goods,userId);
    }
}
