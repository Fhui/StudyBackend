package com.harry.service.orders;

import com.harry.dao.OrdersMapper;
import com.harry.entity.order.Orders;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author harry
 * @create 2019-03-08 14:58
 **/
@Component
public class OrdersService {

    @Resource
    private OrdersMapper ordersMapper;

    public List<Orders> getAllOrders() {
        return ordersMapper.selectAllOrders();
    }

}
