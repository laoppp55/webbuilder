package com.bizwink.persistence;

import com.bizwink.po.LogUniqueipinfoUrl;
import com.bizwink.po.LogUniqueipinfoUrlExample;
import java.math.BigDecimal;

public interface LogUniqueipinfoUrlMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_UNIQUEIPINFO_URL
     *
     * @mbggenerated
     */
    int countByExample(LogUniqueipinfoUrlExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_UNIQUEIPINFO_URL
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(BigDecimal ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_UNIQUEIPINFO_URL
     *
     * @mbggenerated
     */
    int insert(LogUniqueipinfoUrl record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_UNIQUEIPINFO_URL
     *
     * @mbggenerated
     */
    int insertSelective(LogUniqueipinfoUrl record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_UNIQUEIPINFO_URL
     *
     * @mbggenerated
     */
    LogUniqueipinfoUrl selectByPrimaryKey(BigDecimal ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_UNIQUEIPINFO_URL
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(LogUniqueipinfoUrl record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_UNIQUEIPINFO_URL
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(LogUniqueipinfoUrl record);
}