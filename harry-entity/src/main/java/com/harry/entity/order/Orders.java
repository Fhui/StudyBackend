package com.harry.entity.order;


import com.harry.common.BaseEntity;

import java.math.BigDecimal;


/**
 * @author harry
 * @create 2019-03-08 14:23
 **/
public class Orders extends BaseEntity {

    private Integer ordersId;
    private String name;
    private BigDecimal price;
    private Integer userId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Integer ordersId) {
        this.ordersId = ordersId;
    }
}
