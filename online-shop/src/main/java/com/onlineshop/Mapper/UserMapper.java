package com.onlineshop.Mapper;

import com.onlineshop.DTO.Result;
import com.onlineshop.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    User queryByName(String username);
    User queryByPhone(String phone);
    void createUser(User user);
    User queryByID(String userId);
    void updateUser(User user);
}
