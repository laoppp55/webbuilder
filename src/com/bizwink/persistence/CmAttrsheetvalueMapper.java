package com.bizwink.persistence;

import com.bizwink.po.CmAttrsheetvalue;

public interface CmAttrsheetvalueMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CM_ATTR_SHEET_VALUE
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long c_ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CM_ATTR_SHEET_VALUE
     *
     * @mbggenerated
     */
    int insert(CmAttrsheetvalue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CM_ATTR_SHEET_VALUE
     *
     * @mbggenerated
     */
    int insertSelective(CmAttrsheetvalue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CM_ATTR_SHEET_VALUE
     *
     * @mbggenerated
     */
    CmAttrsheetvalue selectByPrimaryKey(Long c_ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CM_ATTR_SHEET_VALUE
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(CmAttrsheetvalue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CM_ATTR_SHEET_VALUE
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(CmAttrsheetvalue record);
}