package com.bizwink.webapps.feedback;

import java.util.List;

public interface IFeedbackManager{

    public int createFeedbackInfo(FeedBack fd);

    public FeedBack getAFeedbackInfo(int id);

    public int deleteAFeedbackInfo(int id);

    public int answerAFeedbackInfo(FeedBack feedback);

    public int answerAFeedbackInfoFlag(int flag,int id);

    public List getAllFeedbackInfo(int start, int range, String sql, String bgntime, String endtime);

    public int getAllFeedbackInfoNum(String sql, String bgntime, String endtime);

    public int getSiteID(String sitename);
    
}