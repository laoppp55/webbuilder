package com.bizwink.webapps.survey.answer;

import java.util.List;

public interface IAnswerManager {

    int createAnswerUser(Answer answer) throws AnswerException;

    int getSurveyUsersCount(int sid) throws AnswerException;

    int getASurveyUsersCount(int qid) throws AnswerException;

    int getAQuestionUsersCount(String answers,int qid) throws AnswerException;

    void createUserAnswers(int sid, int qid, int uid, String[] answers, int nother, String other) throws AnswerException;

    void createUserinfoForDefine(Answer answer);

    List getUserinfo(int defineid);
}

