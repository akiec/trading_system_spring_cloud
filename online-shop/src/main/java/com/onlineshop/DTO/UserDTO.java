package com.onlineshop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long userId;
    private String userName;
    private Boolean isAdmin;
    private String token;
    private String phone;
    private String nickName;
}
