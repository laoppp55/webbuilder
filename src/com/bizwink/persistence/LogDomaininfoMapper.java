package com.bizwink.persistence;

import com.bizwink.po.LogDomaininfo;
import com.bizwink.po.LogDomaininfoExample;
import java.math.BigDecimal;

public interface LogDomaininfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_DOMAININFO
     *
     * @mbggenerated
     */
    int countByExample(LogDomaininfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_DOMAININFO
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(BigDecimal ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_DOMAININFO
     *
     * @mbggenerated
     */
    int insert(LogDomaininfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_DOMAININFO
     *
     * @mbggenerated
     */
    int insertSelective(LogDomaininfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_DOMAININFO
     *
     * @mbggenerated
     */
    LogDomaininfo selectByPrimaryKey(BigDecimal ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_DOMAININFO
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(LogDomaininfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_DOMAININFO
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(LogDomaininfo record);
}