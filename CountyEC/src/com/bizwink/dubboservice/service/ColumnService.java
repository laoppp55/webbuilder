package com.bizwink.dubboservice.service;

import com.bizwink.po.Column;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by petersong on 16-4-5.
 */
public interface ColumnService {
    List<Column> getColumnsBySiteid(BigDecimal siteid);
    String getJsonDataOfColumnsBySiteid(BigDecimal siteid,BigDecimal samsiteid);
    String getColumnJsonsBySiteid(BigDecimal siteid,BigDecimal samsiteid);
    Column getRootColumn(BigDecimal siteid);
    Column getColumnByID(BigDecimal columnid);
}
