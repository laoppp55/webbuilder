package com.bizwink.mysql.persistence;

import com.bizwink.mysql.po.Siteipinfo;
import com.bizwink.mysql.po.SiteipinfoExample;

public interface SiteipinfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_siteipinfo
     *
     * @mbggenerated
     */
    int countByExample(SiteipinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_siteipinfo
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_siteipinfo
     *
     * @mbggenerated
     */
    int insert(Siteipinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_siteipinfo
     *
     * @mbggenerated
     */
    int insertSelective(Siteipinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_siteipinfo
     *
     * @mbggenerated
     */
    Siteipinfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_siteipinfo
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Siteipinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_siteipinfo
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Siteipinfo record);
}