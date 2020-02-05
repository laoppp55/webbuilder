package com.bizwink.cms.webedit;

import java.util.List;
import com.bizwink.cms.util.*;

public interface IWebEditManager
{
  List getFiles(String filePath) throws CmsException;

  void RenameFile(String filePath,String newFilename,String siteName,int siteID);
  
  void DeleteFile(String filename,String sitename,int siteID,boolean delete);
}