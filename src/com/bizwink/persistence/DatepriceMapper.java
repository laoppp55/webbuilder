package com.bizwink.persistence;

import com.bizwink.po.Dateprice;
import java.math.BigDecimal;

public interface DatepriceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CS_DATE_PRICE
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(BigDecimal ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CS_DATE_PRICE
     *
     * @mbggenerated
     */
    int insert(Dateprice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CS_DATE_PRICE
     *
     * @mbggenerated
     */
    int insertSelective(Dateprice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CS_DATE_PRICE
     *
     * @mbggenerated
     */
    Dateprice selectByPrimaryKey(BigDecimal ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CS_DATE_PRICE
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Dateprice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CS_DATE_PRICE
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Dateprice record);
}