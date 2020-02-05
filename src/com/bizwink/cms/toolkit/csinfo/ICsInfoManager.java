package com.bizwink.cms.toolkit.csinfo;


import java.util.List;

/**
 * Created by Jhon on 2016/11/16.
 */
public interface ICsInfoManager {

    void insertCsinfo(CsInfo csinfo, List list) throws CsInfoException;

    List getcsroomList(int siteID) throws CsInfoException;

    List getCurrentcsroomList(int siteID, int startrow, int range) throws CsInfoException;

    void deleteCsInfo(int id) throws CsInfoException;

    void deleteCsInfoPic(int id) throws CsInfoException;
}
