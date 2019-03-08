package com.harry.service.product.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.harry.service.product.IProductService;
import com.harry.service.product.IPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author harry
 * @create 2019-03-06 15:21
 **/
@Component
public class ProductServiceImpl implements IProductService, Serializable {

//    @Reference(interfaceName ="purchaseService", version = "1.0.0")
    @Autowired
    private IPurchaseService purchaseService;

    public Integer getProductCost(Integer productCost) {
        return productCost - purchaseService.getProductPurchaseCost();
    }

}
