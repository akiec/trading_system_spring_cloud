package com.onlineshop.controller;

import com.onlineshop.Service.OrderService;
import com.onlineshop.Utils.IdCreater;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private IdCreater idCreater;


}
