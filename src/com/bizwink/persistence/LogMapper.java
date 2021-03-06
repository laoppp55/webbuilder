package com.bizwink.persistence;

import com.bizwink.po.Log;
import java.math.BigDecimal;
import java.util.List;

public interface LogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_LOG
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(BigDecimal ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_LOG
     *
     * @mbggenerated
     */
    BigDecimal getLogMainKey();

    int insert(Log record);

    int insertInBatch(List<Log> logs);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_LOG
     *
     * @mbggenerated
     */
    int insertSelective(Log record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_LOG
     *
     * @mbggenerated
     */
    Log selectByPrimaryKey(BigDecimal ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_LOG
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Log record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_LOG
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Log record);
}