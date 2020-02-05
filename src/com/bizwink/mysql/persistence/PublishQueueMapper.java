package com.bizwink.mysql.persistence;

import com.bizwink.mysql.po.PublishQueue;
import com.bizwink.mysql.po.PublishQueueExample;

public interface PublishQueueMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_new_publish_queue
     *
     * @mbggenerated
     */
    int countByExample(PublishQueueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_new_publish_queue
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_new_publish_queue
     *
     * @mbggenerated
     */
    int insert(PublishQueue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_new_publish_queue
     *
     * @mbggenerated
     */
    int insertSelective(PublishQueue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_new_publish_queue
     *
     * @mbggenerated
     */
    PublishQueue selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_new_publish_queue
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(PublishQueue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_new_publish_queue
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(PublishQueue record);
}