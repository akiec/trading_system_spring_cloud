package com.onlineshop.Mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressMapper {

    String searchByUserId(Long userId);
}
