package com.bizwink.persistence;

import com.bizwink.po.WzDetail;
import java.math.BigDecimal;

public interface WzDetailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_WZDETAIL
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(BigDecimal ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_WZDETAIL
     *
     * @mbggenerated
     */
    int insert(WzDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_WZDETAIL
     *
     * @mbggenerated
     */
    int insertSelective(WzDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_WZDETAIL
     *
     * @mbggenerated
     */
    WzDetail selectByPrimaryKey(BigDecimal ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_WZDETAIL
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(WzDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_WZDETAIL
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(WzDetail record);
}