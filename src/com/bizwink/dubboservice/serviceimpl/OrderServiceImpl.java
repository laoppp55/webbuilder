package com.bizwink.dubboservice.serviceimpl;

import com.bizwink.dubboservice.service.OrderService;
import com.bizwink.persistence.OrdersDetailMapper;
import com.bizwink.persistence.OrdersMapper;
import com.bizwink.po.Orders;
import com.bizwink.po.OrdersDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

/**
 * Created by petersong on 16-11-14.
 */
@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrdersDetailMapper ordersDetailMapper;

    @Transactional
    public int createOrder(Orders order,OrdersDetail ordersDetail) {
        Timestamp thedate=new Timestamp(System.currentTimeMillis());
        order.setCREATEDATE(thedate);
        ordersDetail.setCREATEDATE(thedate);
        ordersMapper.insert(order);
        //long id = ordersDetailMapper.getMainkey();
        //ordersDetail.setID(id);
        return ordersDetailMapper.insert(ordersDetail);
    }
}
