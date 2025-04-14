package com.onlineshop.controller;

import com.onlineshop.DTO.LoginDTO;
import com.onlineshop.DTO.Result;
import com.onlineshop.DTO.UserDTO;
import com.onlineshop.Service.PaymentService;
import com.onlineshop.Service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private PaymentService paymentService;
    //账号密码登录
    @PostMapping ("/login")
    public Result login(@RequestBody LoginDTO loginDTO) {
        return userService.login(loginDTO);
    }
    //手机号验证码登录
    @PostMapping("/loginByPhone")
    public Result loginByPhone(@RequestBody LoginDTO loginDTO) {
        return userService.loginByPhone(loginDTO);
    }
    //发送验证码
    @PostMapping("/code")
    public Result sendCode(@RequestParam("phone") String phone) {
        return userService.sendCode(phone);
    }
    //查看用户信息
    @GetMapping ("/{id}")
    public Result userDetails(@PathVariable ("id") String userId) {
        return userService.userDetails(userId);
    }
    //用户信息维护
    @PostMapping("/detail")
    public Result updateUser(@RequestBody UserDTO userDTO) {
        return userService.updateUser(userDTO);
    }
    //待支付信息查看
    @GetMapping("/toPayment/{id}")
    public Result toPayment(@PathVariable("id") String userId) {
        return paymentService.toPayment(userId);
    }
    //修改用户收货
    @PostMapping("/address")
    public Result changeAddress(@RequestBody UserDTO userDTO) {
        return userService.changeAddress(userDTO);
    }
    //注册账号密码
    @PostMapping("/register")
    public Result registerUser(@RequestBody LoginDTO loginDTO) {return userService.registerUser(loginDTO);}
}
