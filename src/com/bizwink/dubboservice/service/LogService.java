package com.bizwink.dubboservice.service;

import com.bizwink.po.Log;

import java.util.List;

/**
 * Created by petersong on 16-12-5.
 */
public interface LogService {
    int CreateOperationLog(Log log);

    int CreateOperationLogInBatch(List<Log> logs);
}
