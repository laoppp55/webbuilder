package com.bizwink.persistence;

import com.bizwink.po.Reserve;
import java.math.BigDecimal;

public interface ReserveMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CS_RESERVE
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(BigDecimal ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CS_RESERVE
     *
     * @mbggenerated
     */
    int insert(Reserve record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CS_RESERVE
     *
     * @mbggenerated
     */
    int insertSelective(Reserve record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CS_RESERVE
     *
     * @mbggenerated
     */
    Reserve selectByPrimaryKey(BigDecimal ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CS_RESERVE
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Reserve record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CS_RESERVE
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Reserve record);
}