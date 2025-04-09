package com.onlineshop.controller;

import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/test")
public class TestController {
    @PostMapping("/post")
    public String post(){
        return "post请求";
    }
    @GetMapping("/get")
    public String get(){
        return "get请求";
    }
}
