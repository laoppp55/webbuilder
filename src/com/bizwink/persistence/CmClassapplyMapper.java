package com.bizwink.persistence;

import com.bizwink.po.CmClassapply;

public interface CmClassapplyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CM_CLASS_APPLY
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long c_ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CM_CLASS_APPLY
     *
     * @mbggenerated
     */
    int insert(CmClassapply record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CM_CLASS_APPLY
     *
     * @mbggenerated
     */
    int insertSelective(CmClassapply record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CM_CLASS_APPLY
     *
     * @mbggenerated
     */
    CmClassapply selectByPrimaryKey(Long c_ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CM_CLASS_APPLY
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(CmClassapply record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_CM_CLASS_APPLY
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(CmClassapply record);
}