package com.bizwink.cms.toolkit;

import java.util.*;
import com.bizwink.cms.util.*;

public interface ICommentManager
{
  void create(Comment comment) throws CmsException;

  List getComments(int siteID,int articleID) throws CmsException;

  boolean SendMail(String username,String send,String to,String url)
      throws CmsException;

  List getComments(int siteID) throws CmsException;

  Comment getComment(int ID) throws CmsException;

  void delete(int ID) throws CmsException;
}