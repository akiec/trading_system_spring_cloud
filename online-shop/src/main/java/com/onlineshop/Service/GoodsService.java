package com.onlineshop.Service;

import com.onlineshop.DTO.Result;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


public interface GoodsService {
    Result details(Long goodsId);

    Result searchByPage(int page,int pageSize);

    Result searchByType(String type, int page, int pageSize);

    Result searchByName(String name, int page, int pageSize);
}
