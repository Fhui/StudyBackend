package com.harry.service.orders;

import com.alibaba.fastjson.JSON;
import com.harry.common.BaseService;
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
public class OrdersService extends BaseService<Orders, Integer> {

    private final String KEYS = "ORDERS_INFO";
    @Resource
    private OrdersMapper ordersMapper;

    public List<Orders> getAllOrders() {
        List<Orders> orders = ordersMapper.selectAllOrders();
        put(KEYS, JSON.toJSONString(orders));
        return orders;
    }

    @Override
    protected String getRedisKey() {
        return KEYS;
    }
}
