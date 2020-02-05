package com.bizwink.dubboservice.serviceimpl;

import com.bizwink.dubboservice.service.LogService;
import com.bizwink.persistence.LogMapper;
import com.bizwink.po.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by petersong on 16-12-5.
 */
@Service
public class LogServiceImpl implements LogService{
    @Autowired
    private LogMapper logMapper;

    public int CreateOperationLog(Log log) {
        return logMapper.insert(log);
    }

    public int CreateOperationLogInBatch(List<Log> logs) {
        //给每条记录设置主键
        for(int ii=0; ii<logs.size();ii++) {
            logs.get(ii).setID(logMapper.getLogMainKey());
        }

        return logMapper.insertInBatch(logs);
    }
}
