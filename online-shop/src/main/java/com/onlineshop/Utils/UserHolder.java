package com.onlineshop.Utils;

import com.onlineshop.DTO.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserHolder {
    //获取当前线程中的UserDTO对象
    private static ThreadLocal<UserDTO> threadLocal = new ThreadLocal<>();

    public static UserDTO getUser() {return threadLocal.get();}
    public static void setUser(UserDTO user) {threadLocal.set(user);}
    public static void removeUser() {threadLocal.remove();}
}
