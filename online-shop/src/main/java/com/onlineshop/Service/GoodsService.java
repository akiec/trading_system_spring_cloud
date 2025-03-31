package com.onlineshop.Service;

import com.onlineshop.DTO.Result;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


public interface GoodsService {
    Result details(Long goodsId);
}
