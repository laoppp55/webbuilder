package com.bizwink.cms.business.deliver;

import java.util.*;
import com.bizwink.cms.util.*;

public interface IDeliverManager
{
  void create(Deliver deliver) throws CmsException;

  void update(Deliver deliver) throws CmsException;

  List getCityList(Deliver deliver) throws CmsException;

  void delete(Deliver deliver) throws CmsException;

  void createFee(List feeList) throws CmsException;

  List getCityFeeList(Deliver deliver) throws CmsException;

  void createExpCorp(String expName) throws CmsException;

  void updateExpCorp(int expID,String expName) throws CmsException;

  void deleteExpCorp(int expID) throws CmsException;

  List getExpCorpList() throws CmsException;

  void createExpFee(List feeList) throws CmsException;

  List getExpFeeList(Deliver deliver) throws CmsException;
}
