package com.bizwink.persistence;

import com.bizwink.po.Multimedia;
import java.math.BigDecimal;

public interface MultimediaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_MULTIMEDIA
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(BigDecimal ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_MULTIMEDIA
     *
     * @mbggenerated
     */
    int insert(Multimedia record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_MULTIMEDIA
     *
     * @mbggenerated
     */
    int insertSelective(Multimedia record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_MULTIMEDIA
     *
     * @mbggenerated
     */
    Multimedia selectByPrimaryKey(BigDecimal ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_MULTIMEDIA
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Multimedia record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_MULTIMEDIA
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Multimedia record);
}