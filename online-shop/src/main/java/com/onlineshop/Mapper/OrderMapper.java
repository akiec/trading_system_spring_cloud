package com.onlineshop.Mapper;

import com.onlineshop.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface OrderMapper {
    List<Order> checkOrder(String userId);

    void createOrder(Order order);

    Order getDetails(String orderId);

    void delete(String orderId);

    void setStatus(String orderId,Integer status);

    @Select("select * from Orders where customerId =#{userId}")
    Order selectByUserID(String userId);
}
