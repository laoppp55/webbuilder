package com.bizwink.log;

import com.bizwink.cms.news.Article;

import java.util.*;

public interface ILogManager {
    List getEditorLogInfo(int siteID, String where) throws LogException;

    List<PublishArticleListOfWeek> getArticlesOfToday(int siteid,String deptname) throws LogException;

    List<PublishArticleCountOfDept> getPublishArticleCountOFMonthOfDept(int siteid) throws LogException;

    List getEditorLogInfoGroupbyActTime(int siteID, String editor, int flag, int startrow, int range, String where) throws LogException;

    int getEditorLogInfoNumGroupbyActTime(int siteID, String editor, int flag, String where) throws LogException;

    int getEditorDetailLogInfoNumGroupbyActTime(int siteID, String editor, int flag, String date) throws LogException;

    List getEditorDetailLogInfoGroupbyActTime(int siteID, String editor, int flag, int startrow, int range, String date) throws LogException;

    List getGroupsEditorLogInfo(int groupId) throws LogException;

    List getGroupsEditorLogInfo(int groupId, String where) throws LogException;

    int LogSearchKeyword(int siteID, String userip, String keyword) throws LogException;

    List getLoginfoInPage(int range,int currentpage,int siteid,List<Integer> columnids,List<Integer> deptids,List<String> editors,String sdatetime,String edatetime,String keyword,int optype) throws LogException;

    int getCountLoginfo(int siteid,List<Integer> columnids,List<Integer> deptids,List<String> editors,String sdatetime,String edatetime,String keyword,int optype)  throws LogException;

    LogColumnTree getLogColumnTree(int siteid)  throws LogException;

    LogColumnTree getWorkingByCol(int siteid,LogColumnTree logColumnTree)  throws LogException;
}