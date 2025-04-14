package com.onlineshop.DTO;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    private String userName;
    private Boolean isAdmin;
    private String token;
    private String phone;
    private String nickName;
    private String address;
}
