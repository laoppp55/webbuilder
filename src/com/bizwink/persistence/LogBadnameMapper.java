package com.bizwink.persistence;

import com.bizwink.po.LogBadname;
import com.bizwink.po.LogBadnameExample;

public interface LogBadnameMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_BADNAME
     *
     * @mbggenerated
     */
    int countByExample(LogBadnameExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_BADNAME
     *
     * @mbggenerated
     */
    int insert(LogBadname record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_BADNAME
     *
     * @mbggenerated
     */
    int insertSelective(LogBadname record);
}