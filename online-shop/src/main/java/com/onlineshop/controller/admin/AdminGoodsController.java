package com.onlineshop.controller.admin;

import com.onlineshop.DTO.Result;
import com.onlineshop.Service.admin.AdminGoodsService;
import com.onlineshop.entity.Goods;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
//此部分为实现管理员的增删改方面
@RequestMapping("/admin")
public class AdminGoodsController {
    @Resource
    private AdminGoodsService goodsService;
    //新增商品
    @PostMapping("/add/{id}")
    public Result addGoods(@RequestBody Goods goods,@PathVariable("id") String userId) {
        return goodsService.addGoods(goods,userId);
    }
    //删除商品
    @DeleteMapping("/delete/{id}")
    public Result deleteGoods(@PathVariable ("id") String goodsId) {
        return goodsService.delete(goodsId);
    }
    //修改商品
    @PostMapping("/update")
    public Result updateGoods(@RequestBody Goods goods) {
        return goodsService.updateGoods(goods);
    }
}
