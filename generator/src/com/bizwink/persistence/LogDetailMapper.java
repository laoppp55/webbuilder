package com.bizwink.persistence;

import com.bizwink.po.LogDetail;
import com.bizwink.po.LogDetailExample;

public interface LogDetailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_DETAIL
     *
     * @mbggenerated
     */
    int countByExample(LogDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_DETAIL
     *
     * @mbggenerated
     */
    int insert(LogDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_DETAIL
     *
     * @mbggenerated
     */
    int insertSelective(LogDetail record);
}