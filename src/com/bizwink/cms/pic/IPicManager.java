package com.bizwink.cms.pic;

import com.bizwink.cms.news.Turnpic;

import java.util.List;


public interface IPicManager{

    void createPic(List list);

    int getPicInfoNum(String picname,int siteid,int type);

    List getPicInfo(String picname,int siteid,int type,int start,int range);

    String saveOnePicture(Pic picture);

    List saveMorePicture(List picture);

    public List<Turnpic> getTurpics(int articleid);

    boolean existThePicture(String name, int siteid);

    String AddWaterMarkToImg(String orgfilepath,String notes);
}