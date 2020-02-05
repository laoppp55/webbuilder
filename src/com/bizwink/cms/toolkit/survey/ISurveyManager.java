package com.bizwink.cms.toolkit.survey;

import java.util.*;

public interface ISurveyManager
{
  void Create_XML(int type, Survey survey) throws SurveyException;

  List Get_XML(int type, String ID) throws SurveyException;

  void Update_XML(int type,Survey survey) throws SurveyException;

  void Delete_XML(int type,String ID,String answer) throws SurveyException;

  boolean Query_Title(int type,String name,String ID) throws SurveyException;

  List Get_ITEM_XML(String ID) throws SurveyException;

  void Update_Vote(String ID,String item[]) throws SurveyException;
}