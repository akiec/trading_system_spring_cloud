package com.onlineshop.controller;

import com.onlineshop.DTO.Result;
import com.onlineshop.Service.GoodsService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
//此处无商品的增删改功能，该部分功能放入adminController中实现
//此处为普通用户视角的商品功能
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Resource
    private GoodsService goodsService;
    //分页查询多条商品
    @PostMapping("")
    public Result searchByPage(@RequestParam(value = "page",defaultValue = "1") int page, @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
        return goodsService.searchByPage(page,pageSize);
    }
    //查看详细信息
    @PostMapping("/details/{id}")
    public Result details(@PathVariable ("id") Long goodsId){
        return goodsService.details(goodsId);
    }
    //查看某一种类商品分页查询
    @PostMapping("/type")
    public Result type(@PathVariable("type") String type,@RequestParam(value = "page",defaultValue = "1") int page, @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
        return goodsService.searchByType(type,page,pageSize);
    }
    //根据名称检索商品
    @PostMapping("/search/{name}")
    public Result name(@PathVariable("name") String name,@RequestParam(value = "page",defaultValue = "1") int page, @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
        return goodsService.searchByName(name,page,pageSize);
    }



}
