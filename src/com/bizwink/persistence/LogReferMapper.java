package com.bizwink.persistence;

import com.bizwink.po.LogRefer;
import com.bizwink.po.LogReferExample;

public interface LogReferMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_REFER
     *
     * @mbggenerated
     */
    int countByExample(LogReferExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_REFER
     *
     * @mbggenerated
     */
    int insert(LogRefer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_REFER
     *
     * @mbggenerated
     */
    int insertSelective(LogRefer record);
}