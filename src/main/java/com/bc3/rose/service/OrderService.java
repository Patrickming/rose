package com.bc3.rose.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bc3.rose.entity.Orders;

public interface OrderService extends IService<Orders> {
    /**
     * 用户下单
     * @param orders
     */
    void sumbit(Orders orders);
}
