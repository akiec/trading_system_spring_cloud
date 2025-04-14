package com.onlineshop.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    //用户id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    //用户名
    private String userName;
    //密码
    private String passWord;
    //是否为管理员
    private Boolean isAdmin;
    //电话号码
    private String phone;
    //token
    private String token;
    //创建时间
    private LocalDateTime createTime;
    //修改时间
    private LocalDateTime updateTime;
    //用户昵称
    private String nickName;
    //用户收货地址
    private String address;

}
