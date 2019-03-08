package com.harry.web.controller;

import com.harry.service.HelloDubboService;
import com.harry.service.product.IProductService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @author harry
 * @create 2019-03-06 11:52
 **/
@RestController
public class HelloDubboController {

    @Resource
    private IProductService productService;
    @Resource
    private HelloDubboService dubboService;

    @RequestMapping(value = "hello/{what}", method = RequestMethod.GET)
    public String sayWhat(@PathVariable String what) {
        return dubboService.sayWhat(what);
    }

    @RequestMapping(value = "/getPurchaseCost", method = RequestMethod.GET)
    public String getPurchaseCost(int cost) {
        return "该产品总共消费 ：" + productService.getProductCost(cost);
    }

}
