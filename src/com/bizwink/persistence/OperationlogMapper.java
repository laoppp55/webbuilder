package com.bizwink.persistence;

import com.bizwink.po.Operationlog;
import java.math.BigDecimal;

public interface OperationlogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CS_OPERATIONLOG
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(BigDecimal ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CS_OPERATIONLOG
     *
     * @mbggenerated
     */
    int insert(Operationlog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CS_OPERATIONLOG
     *
     * @mbggenerated
     */
    int insertSelective(Operationlog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CS_OPERATIONLOG
     *
     * @mbggenerated
     */
    Operationlog selectByPrimaryKey(BigDecimal ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CS_OPERATIONLOG
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Operationlog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CS_OPERATIONLOG
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Operationlog record);
}