package com.onlineshop.controller;

import com.onlineshop.DTO.Result;
import com.onlineshop.Service.GoodsService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//此处无商品的增删改功能，该部分功能放入adminController中实现
//此处为普通用户视角的商品功能
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Resource
    private GoodsService goodsService;
    //分页查询多条商品
    @PostMapping("/{page}")
    public Result searchByPage(@PathVariable ("page") int page){
        return goodsService.searchByPage(page);
    }
    //查看详细信息
    @PostMapping("/details/{id}")
    public Result details(@PathVariable ("id") Long goodsId) {
        return goodsService.details(goodsId);
    }





}
