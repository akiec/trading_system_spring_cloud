package com.onlineshop.Mapper;

import com.onlineshop.DTO.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressMapper {

    String searchByUserId(Long userId);

    void changeAddress(UserDTO userDTO);
}
