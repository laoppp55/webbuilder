package com.bizwink.service;

import com.bizwink.persistence.ColumnMapper;
import com.bizwink.po.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 17-5-12.
 */
@Service
public class ColumnService {
    @Autowired
    private ColumnMapper columnMapper;

    public String  getColumns(BigDecimal siteid) {
        List<Column> columns= columnMapper.getColumnsBySiteid(siteid);
        String buf = null;
        if (columns.size() > 0) {
            buf = "[\r\n";
            for(int ii=0;ii<columns.size();ii++){
                Column column = (Column)columns.get(ii);
                buf = buf + "{id:" + column.getID().intValue() + ",pId:"+ column.getPARENTID().intValue() + ",name:\"" + column.getCNAME() +"\",url:\"\"" + ",open:\"true\"" +"},\r\n";
            }
            buf = buf + "]";
        }
        return buf;
    }

    public List<Column> getColumnsBySiteid(BigDecimal siteid) {
        return columnMapper.getColumnsBySiteid(siteid);
    }

    public List<Column>  getSubColumnsIncludeParentColumnByParentID(BigDecimal pid) {
        return columnMapper.getSubColumnsIncludeParentColumnByParentID(pid);
    }
    public List<Column>  getSubColumns(BigDecimal pid) {
        return columnMapper.getSubColumnsByParentID(pid);
    }

    public BigDecimal  getSubColumnsCount(BigDecimal columnid) {
        return columnMapper.getSubColumnsCountByParentID(columnid);
    }

    public Column getRootColumnBySiteid(BigDecimal siteid) {
        return columnMapper.getRootColumnBySiteid(siteid);
    }

    public Column getColumn(BigDecimal columnid) {
        return columnMapper.selectByPrimaryKey(columnid);
    }

}
