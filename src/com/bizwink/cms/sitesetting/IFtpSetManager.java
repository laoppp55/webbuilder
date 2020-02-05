package com.bizwink.cms.sitesetting;

import java.util.*;

public interface IFtpSetManager
{
  void create(FtpInfo ftpInfo) throws SiteInfoException;

  void update(FtpInfo ftpInfo) throws SiteInfoException;

  void remove(int ID) throws SiteInfoException;

  FtpInfo getFtpInfo(int ID) throws SiteInfoException;

  List getFtpInfos(int siteID) throws SiteInfoException;

  List getOtherFtpInfos(int siteID) throws SiteInfoException;

  List getFtpInfoList(int siteID) throws SiteInfoException;

  List getFtpInfoListForWML(int siteID,int status) throws SiteInfoException;
}