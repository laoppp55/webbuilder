package com.bizwink.webapps.survey.define;


import java.util.List;

public interface IDefineManager {

  List getAllDefineSurvey(int siteid, int startrow, int range) throws DefineException;

  List getAllDefineQuestionsBySID(int sid) throws DefineException;

  List getAllDefineAnswersByQID(int qid) throws DefineException;

  Define getADefineSurvey(int sid) throws DefineException;

  Define getADefineQuestion(int qid) throws DefineException;

  Define getADefineAnswer(int aid) throws DefineException;

  void createDefineSurvey(Define define) throws DefineException;

  void createDefineQuestion(Define define) throws DefineException;

  void createDefineAnswer(Define define) throws DefineException;

  void deleteDefineSurvey(int id) throws DefineException;

  void deleteDefineQuestion(int qid) throws DefineException;

  void deleteDefineAnswer(int aid) throws DefineException;

  void updateDefineSurvey(Define define) throws DefineException;

  void updateDefineQuestion(Define define) throws DefineException;

  void updateDefineAnswer(Define define) throws DefineException;

  void createDefinePicAnswer(Define define) throws DefineException;
  
  int getAllDefineSurveyNum(int siteid) throws DefineException;

    public int updateSurveyFlag(int id, int flag);

    public int getWebViewSurvey(int siteid);

    List getAllDefineSurveyForMark(int siteid) throws DefineException;
}

