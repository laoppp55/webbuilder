package com.bizwink.persistence;

import com.bizwink.po.Department;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface DepartmentMapper {

    BigDecimal getMainKey();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_DEPARTMENT
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(BigDecimal ID);

    int deleteByOrgID(BigDecimal orgid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_DEPARTMENT
     *
     * @mbggenerated
     */
    int insert(Department record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_DEPARTMENT
     *
     * @mbggenerated
     */
    int insertSelective(Department record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_DEPARTMENT
     *
     * @mbggenerated
     */
    Department selectByPrimaryKey(BigDecimal ID);

    List<Department> getDepartmentsByOrgid(Map params);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_DEPARTMENT
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Department record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_DEPARTMENT
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Department record);

    int updateDeptinfo(Department record);
}