package com.bizwink.persistence;

import com.bizwink.po.CmTempletdefinition;

public interface CmTempletdefinitionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CM_TEMPLET_DEFINITION
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long c_ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CM_TEMPLET_DEFINITION
     *
     * @mbggenerated
     */
    int insert(CmTempletdefinition record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CM_TEMPLET_DEFINITION
     *
     * @mbggenerated
     */
    int insertSelective(CmTempletdefinition record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CM_TEMPLET_DEFINITION
     *
     * @mbggenerated
     */
    CmTempletdefinition selectByPrimaryKey(Long c_ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CM_TEMPLET_DEFINITION
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(CmTempletdefinition record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CM_TEMPLET_DEFINITION
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(CmTempletdefinition record);
}