package com.bizwink.service;

import com.bizwink.persistence.WzClassMapper;
import com.bizwink.po.WzClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 18-7-14.
 */
@Service
public class WuziclassService {
    @Autowired
    private WzClassMapper wzClassMapper;

    public List<WzClass> getMainClasses(BigDecimal customerid) {
        return wzClassMapper.getMainWzClass(customerid);
    }
}
