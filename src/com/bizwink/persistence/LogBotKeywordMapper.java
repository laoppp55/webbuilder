package com.bizwink.persistence;

import com.bizwink.po.LogBotKeyword;
import com.bizwink.po.LogBotKeywordExample;

public interface LogBotKeywordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_BOTKEYWORD
     *
     * @mbggenerated
     */
    int countByExample(LogBotKeywordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_BOTKEYWORD
     *
     * @mbggenerated
     */
    int insert(LogBotKeyword record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TLOG_BOTKEYWORD
     *
     * @mbggenerated
     */
    int insertSelective(LogBotKeyword record);
}