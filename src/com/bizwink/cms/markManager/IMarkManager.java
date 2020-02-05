package com.bizwink.cms.markManager;

import com.bizwink.cms.tree.Tree;

import java.util.*;
import java.sql.*;

public interface IMarkManager
{
    int Create(mark mark) throws markException;

    void Update(mark mark) throws markException;

    mark getAMark(int markID) throws markException;

    List listAllMarks(int siteid) throws markException;

    List listAllMarks(int siteid,int columnid) throws markException;

    List getMarksByType(int siteid,int marktype) throws markException;

    String getAMarkContent(int markID) throws markException;

    String getRelatedColumnIDs(int siteid,Tree colTree,int columnID,String content) throws markException;

    int getMarkID(int templateID,int orderNum) throws markException;

    String getMarkCode(String markChineseName) throws markException;

    List getArticleListMark(int siteid,int columnid);

    List getArticleListUpdate(int articleid);

    List getArticleListQuery(int articleid);

    int getArticlenumForArticleListMark(int markID) throws markException;

    int updateArticlenumForArticleListMark(int markID,int artnum) throws markException;
}
