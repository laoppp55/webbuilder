package com.bizwink.mysql.persistence;

import com.bizwink.mysql.po.MembersRights;
import com.bizwink.mysql.po.MembersRightsExample;

public interface MembersRightsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_members_rights
     *
     * @mbggenerated
     */
    int countByExample(MembersRightsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_members_rights
     *
     * @mbggenerated
     */
    int insert(MembersRights record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_members_rights
     *
     * @mbggenerated
     */
    int insertSelective(MembersRights record);
}