package com.bizwink.mysql.persistence;

import com.bizwink.mysql.po.OrderDetail;

public interface OrderDetailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_orders_detail
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_orders_detail
     *
     * @mbggenerated
     */
    int insert(OrderDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_orders_detail
     *
     * @mbggenerated
     */
    int insertSelective(OrderDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_orders_detail
     *
     * @mbggenerated
     */
    OrderDetail selectByPrimaryKey(Long ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_orders_detail
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(OrderDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_orders_detail
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(OrderDetail record);
}