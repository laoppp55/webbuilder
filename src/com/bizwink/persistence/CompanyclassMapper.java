package com.bizwink.persistence;

import com.bizwink.po.Companyclass;
import java.math.BigDecimal;

public interface CompanyclassMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_COMPANYCLASS
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(BigDecimal ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_COMPANYCLASS
     *
     * @mbggenerated
     */
    int insert(Companyclass record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_COMPANYCLASS
     *
     * @mbggenerated
     */
    int insertSelective(Companyclass record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_COMPANYCLASS
     *
     * @mbggenerated
     */
    Companyclass selectByPrimaryKey(BigDecimal ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_COMPANYCLASS
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Companyclass record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_COMPANYCLASS
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Companyclass record);
}