package com.onlineshop.Service;

import com.onlineshop.DTO.LoginDTO;
import com.onlineshop.DTO.Result;
import com.onlineshop.DTO.UserDTO;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


public interface UserService {

    Result login(LoginDTO loginDTO);

    Result loginByPhone(LoginDTO loginDTO);


    Result sendCode(String phone);

    Result userDetails(Long userId);

    Result updateUser(UserDTO userDTO);

    Result changeAddress(UserDTO userDTO);
}
