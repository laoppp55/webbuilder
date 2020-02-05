package com.bizwink.service;

import com.bizwink.persistence.SjslogMapper;
import com.bizwink.po.Sjslog;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by petersong on 15-12-13.
 */
@Service
public class BjSjslogService {
    @Autowired
    private SjslogMapper sjslogMapper;
    private static Logger logger = Logger.getLogger(BjSjslogService.class.getName());

    public BigDecimal getKey() {
        return sjslogMapper.getKey();
    };

    public int insertSjsLogInfo(Sjslog loginfo) {
        return sjslogMapper.insert(loginfo);
    }
}
