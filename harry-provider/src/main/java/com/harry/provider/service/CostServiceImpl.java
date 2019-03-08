package com.harry.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.harry.service.product.IPurchaseService;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author harry
 * @create 2019-03-06 15:10
 **/
@Service(interfaceName = "purchaseService", version = "1.0.0")
public class CostServiceImpl implements IPurchaseService, Serializable {

    @Override
    public Integer getProductPurchaseCost() {
        return 100;
    }
}
