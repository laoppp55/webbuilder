package com.bizwink.dubboservice.service;

import com.bizwink.po.Orders;
import com.bizwink.po.OrdersDetail;

/**
 * Created by petersong on 16-11-14.
 */
public interface OrderService {
    int createOrder(Orders order,OrdersDetail ordersDetail);
}
