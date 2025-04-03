package com.onlineshop.controller.admin;

import com.onlineshop.DTO.Result;
import com.onlineshop.Service.admin.GoodsService;
import com.onlineshop.entity.Goods;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
//此部分为实现管理员的增删改方面
@RequestMapping("/admin")
public class AdminGoodsController {
    @Resource
    private GoodsService goodsService;
    //新增商品
    @PostMapping("/add")
    public Result addGoods(@RequestBody Goods goods) {
        return goodsService.addGoods(goods);
    }
    //删除商品
    @DeleteMapping("/delete/{id}")
    public Result deleteGoods(@PathVariable ("id") Long goodsId) {
        return goodsService.delete(goodsId);
    }
    //修改商品
    @PostMapping("/update")
    public Result updateGoods(@RequestBody Goods goods) {
        return goodsService.updateGoods(goods);
    }
}
