package com.onlineshop.Mapper;

import com.onlineshop.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface OrderMapper {
    List<Order> checkOrder(Long userId);

    void createOrder(Order order);

    Order getDetails(Long orderId);

    void delete(Long orderId);

    void setStatus(Long orderId,Integer status);

    @Select("select * from Orders where customerId =#{userId}")
    Order selectByUserID(Long userId);
}
