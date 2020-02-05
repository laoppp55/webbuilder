package com.bizwink.persistence;

import com.bizwink.po.SitesNumber;

import java.util.List;

public interface SitesNumberMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_SITES_NUMBER
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String IPADDRESS);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_SITES_NUMBER
     *
     * @mbggenerated
     */
    int insert(SitesNumber record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_SITES_NUMBER
     *
     * @mbggenerated
     */
    int insertSelective(SitesNumber record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_SITES_NUMBER
     *
     * @mbggenerated
     */
    SitesNumber selectByPrimaryKey(String IPADDRESS);

    List<SitesNumber> selectMinSiteNumberRecord();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_SITES_NUMBER
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SitesNumber record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_SITES_NUMBER
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(SitesNumber record);
}