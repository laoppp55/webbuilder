package com.bizwink.persistence;

import com.bizwink.po.LeaveWord;
import com.bizwink.po.LeaveWordExample;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface LeaveWordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_LEAVEWORD
     *
     * @mbggenerated
     */
    int countByExample(LeaveWordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_LEAVEWORD
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(BigDecimal id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_LEAVEWORD
     *
     * @mbggenerated
     */
    int insert(LeaveWord record);

    int insertLeaveWord(LeaveWord record);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_LEAVEWORD
     *
     * @mbggenerated
     */
    int insertSelective(LeaveWord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_LEAVEWORD
     *
     * @mbggenerated
     */
    LeaveWord selectByPrimaryKey(BigDecimal id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_LEAVEWORD
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(LeaveWord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TBL_LEAVEWORD
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(LeaveWord record);

    Integer countLeaveWord (Map<String, Object> param);

    List<LeaveWord> getLeaveWordList(Map<String, Object> param);

    int deleteLeaveWords(String[] LeaveWordIds);
}