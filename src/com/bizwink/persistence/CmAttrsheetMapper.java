package com.bizwink.persistence;

import com.bizwink.po.CmAttrsheet;

public interface CmAttrsheetMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CM_ATTR_SHEET
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long c_ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CM_ATTR_SHEET
     *
     * @mbggenerated
     */
    int insert(CmAttrsheet record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CM_ATTR_SHEET
     *
     * @mbggenerated
     */
    int insertSelective(CmAttrsheet record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CM_ATTR_SHEET
     *
     * @mbggenerated
     */
    CmAttrsheet selectByPrimaryKey(Long c_ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CM_ATTR_SHEET
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(CmAttrsheet record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CM_ATTR_SHEET
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(CmAttrsheet record);
}