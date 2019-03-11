package com.harry.web.controller;

import com.alibaba.fastjson.JSON;
import com.harry.entity.order.Orders;
import com.harry.service.HelloDubboService;
import com.harry.service.orders.OrdersService;
import com.harry.service.product.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author harry
 * @create 2019-03-06 11:52
 **/
@RestController
public class HelloDubboController {

    private final static Logger logger = LoggerFactory.getLogger(HelloDubboController.class);

    @Resource
    private IProductService productService;
    @Resource
    private HelloDubboService dubboService;
    @Resource
    private OrdersService ordersService;
    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping(value = "hello/{what}", method = RequestMethod.GET)
    public String sayWhat(@PathVariable String what) {
        return dubboService.sayWhat(what);
    }

    @RequestMapping(value = "/getPurchaseCost", method = RequestMethod.GET)
    public String getPurchaseCost(int cost) {
        return "该产品总共消费 ：" + productService.getProductCost(cost);
    }

    @RequestMapping(value = "/getAllOrders", method = RequestMethod.GET)
    public List<Orders> getAllOrders() {
        logger.info("start----------logging");
        List<Orders> allOrders = ordersService.getAllOrders();
        kafkaTemplate.send("orders", JSON.toJSONString(allOrders));
        return allOrders;
    }

}
