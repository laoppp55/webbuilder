package com.bizwink.persistence;

import com.bizwink.po.Roominfo;
import java.math.BigDecimal;

public interface RoominfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CS_ROOM_INFO
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(BigDecimal ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CS_ROOM_INFO
     *
     * @mbggenerated
     */
    int insert(Roominfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CS_ROOM_INFO
     *
     * @mbggenerated
     */
    int insertSelective(Roominfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CS_ROOM_INFO
     *
     * @mbggenerated
     */
    Roominfo selectByPrimaryKey(BigDecimal ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CS_ROOM_INFO
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Roominfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CS_ROOM_INFO
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Roominfo record);
}