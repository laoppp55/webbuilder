package com.bizwink.persistence;

import com.bizwink.po.LogOS;
import com.bizwink.po.LogOSExample;

public interface LogOSMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_OS
     *
     * @mbggenerated
     */
    int countByExample(LogOSExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_OS
     *
     * @mbggenerated
     */
    int insert(LogOS record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_OS
     *
     * @mbggenerated
     */
    int insertSelective(LogOS record);
}