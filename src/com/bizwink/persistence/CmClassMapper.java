package com.bizwink.persistence;

import com.bizwink.po.CmClass;

public interface CmClassMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CM_CLASS
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long c_ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CM_CLASS
     *
     * @mbggenerated
     */
    int insert(CmClass record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CM_CLASS
     *
     * @mbggenerated
     */
    int insertSelective(CmClass record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CM_CLASS
     *
     * @mbggenerated
     */
    CmClass selectByPrimaryKey(Long c_ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CM_CLASS
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(CmClass record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CM_CLASS
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(CmClass record);
}