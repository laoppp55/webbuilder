package com.bizwink.persistence;

import com.bizwink.po.WzServicearea;
import java.math.BigDecimal;

public interface WzServiceareaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_WZSERVICEAREA
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(BigDecimal ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_WZSERVICEAREA
     *
     * @mbggenerated
     */
    int insert(WzServicearea record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_WZSERVICEAREA
     *
     * @mbggenerated
     */
    int insertSelective(WzServicearea record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_WZSERVICEAREA
     *
     * @mbggenerated
     */
    WzServicearea selectByPrimaryKey(BigDecimal ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_WZSERVICEAREA
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(WzServicearea record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_WZSERVICEAREA
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(WzServicearea record);
}