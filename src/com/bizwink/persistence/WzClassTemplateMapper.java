package com.bizwink.persistence;

import com.bizwink.po.WzClassTemplate;
import java.math.BigDecimal;

public interface WzClassTemplateMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_WZCLASS_TEMPLATE
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(BigDecimal ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_WZCLASS_TEMPLATE
     *
     * @mbggenerated
     */
    int insert(WzClassTemplate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_WZCLASS_TEMPLATE
     *
     * @mbggenerated
     */
    int insertSelective(WzClassTemplate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_WZCLASS_TEMPLATE
     *
     * @mbggenerated
     */
    WzClassTemplate selectByPrimaryKey(BigDecimal ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_WZCLASS_TEMPLATE
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(WzClassTemplate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_WZCLASS_TEMPLATE
     *
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(WzClassTemplate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_WZCLASS_TEMPLATE
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(WzClassTemplate record);
}