package com.bizwink.cms.toolkit.companyinfo;

import java.util.List;


public interface ICompanyinfoManager {

    retCompany addCompanyInfo(Companyinfo companyinfo,String dir);                                         //添加企业信息

    retCompany modifyCompany(Companyinfo company,String dir);                                              //修改公司信息

    List getCompanyPicsInfos(int siteid,int articleid);

    List getCompanyMediasInfos(int siteid,int articleid);

    int getCompanyNum(String sql);                                                                       //取得该站点下某个分类的企业数量

    List getCompanyInfos(int siteid,int columnid,int start, int range,String username,int flag);       //分页取出该站点下某个分类的企业信息

    int getCompanyInfos_num(int siteid,int columnid,String username,int flag);

    void delCompany(int id,int siteid);                                                               //删除公司

    Companyinfo getACompanyInfo(int id,int siteid);                                                   //根据ID得到公司的一个对象

    Companyinfo getACompanyInfoBySiteid(int siteid);

    List getAllCompanyInfos(int siteid,int start, int range,String username,int flag);

    Companyinfo searchCompanyInfo(String keyword);

    int getClassidByName(String colname,int siteid);

    List getCompanyList(int siteid);                                                                  //取出该站点下所有企业信息

    int CreateXML(int siteid,String pathXML);

    companyClass getCompanyClass(int id);

    List getCompanyClassList(int siteid);

    void create(companyClass companyclass);

    void remove(int id,int siteID) throws Exception;

    void update(companyClass companyclass,int siteid);

    String getIndexExtName(int siteID) throws Exception;

    boolean duplicateEnName(int parentColumnID, String enName);

    int createSpotInfo(ScenicSpot spot);

    List getSpotListByPerson(int siteid);

    List getMemberList();

    retCompany savePicAndMedias(int siteid,int columnid,int companyid,List pictures,List medias,String dir);

    //培训会
    void addMeetings(Meettings meettings) throws Exception;

    List getAllmeetings(int startrow, int range) throws Exception ;

    int getAllmeetingsNum() throws Exception;

    void deleteMeetings(int id) throws Exception;

    Meettings getMeeting(int id) throws Exception;

    void updateMeeting(int id,Meettings meettings) throws Exception;

    List getMeetingTime() throws Exception;

    String getMeetingaddress(int id) throws Exception;

    int sub_baoming(Meetting_sign meetting_sign,List list) throws  Exception;

    List getAllmeeting_sign(int startrow, int range) throws Exception;

    int getAllmeetingSignNum() throws Exception;

    List getmeeting_sign_part(int startrow, int range,Long orderid) throws Exception;

    int getmeetingSignpartNum(Long orderid) throws Exception;

}

