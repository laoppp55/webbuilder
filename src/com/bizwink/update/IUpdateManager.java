package com.bizwink.update;

import com.bizwink.cms.util.CmsException;

import java.util.List;

public interface IUpdateManager{

    List getAllTemplateInfos() throws CmsException;

    boolean replaceMark(int id, int columnid, String content) throws CmsException;

    void updateTemplateContent(int id, String content) throws CmsException;

    List getAllColumnMark() throws CmsException;

    void updateMarkContent(int id, String content) throws CmsException;
}