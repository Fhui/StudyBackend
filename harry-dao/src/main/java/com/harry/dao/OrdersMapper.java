package com.harry.dao;

import com.harry.entity.order.Orders;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * @author harry
 * @create 2019-03-08 14:23
 **/
@Mapper
public interface OrdersMapper {

    List<Orders> selectAllOrders();

}
