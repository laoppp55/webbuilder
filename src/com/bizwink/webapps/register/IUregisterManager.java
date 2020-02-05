package com.bizwink.webapps.register;

import com.bizwink.util.OlympicMembers;
import com.bizwink.webapps.register.*;
import com.bizwink.cms.util.CmsException;

import java.util.List;

public interface IUregisterManager{

    String getRandKeys( int intLength) throws UregisterException;

    String getUsername(int id) throws UregisterException;

    // Uregister getUsername(int id) throws UregisterException;

    int insert_Info(Uregister reg) throws UregisterException;

    int getSiteid(String sitename) throws UregisterException;

    int checkUserExist(String str,int siteid) throws UregisterException;

    int checkUserEmailExist(String str,int siteid) throws UregisterException;

    int getPassword(String memberid, String password,int siteid) throws UregisterException;

    Uregister getUserInfo(String name,int siteid) throws UregisterException;

    int  Update_userinfo(Uregister ure,int siteid)  throws UregisterException;

    Uregister getLogin(String username, String password)  throws UregisterException;

    Uregister login(String usern, String passw,int siteid) throws UregisterException;

    Uregister elogin(String usern, String passw,int siteid) throws UregisterException;

    Uregister getUserinfo(int flag,String username)   throws UregisterException;

    int getUserScores(int userid);

    int getScoresForMoney(String sitename);

    String getSitename(int siteid) throws UregisterException;

    //北京市无线电管理局企业用户注册
    boolean userExist(String userid) throws CmsException;

    int insertRegister(Uregister reg) throws CmsException;

    void updateRsbt(Uregister reg, int id) throws CmsException;

    void deleteRsbtList(int id) throws CmsException;

    List getAllRsbt() throws CmsException;

    List getAllRsbt(int siteid) throws CmsException;

    List getCurrentQueryRsbtList(int id,String sqlstr, int startrow, int range) throws CmsException;

    int getAllRsbtNum(int siteid) throws CmsException;

    List getCurrentRsbtList(int siteid, int startrow, int range) throws CmsException;

    Uregister getByIdrsbt(int id) throws CmsException;

    List getAllOrg1() throws CmsException;

    List getAllOrg2() throws CmsException;

    List getAllOrg3() throws CmsException;
    //北京市无线电管理局企业用户注册项目结束

    //奥运之家项目开始
    void updateUserScores(int scores,int userid);

    Uregister getUregister(int userid);

    int updateUserPassword(int userid,String password);

    int updateUserPassword(String username,String password);

    OlympicMembers loginForOlympic(String usern, String passw) throws UregisterException;

    OlympicMembers getUserInfoForOlympic(String name) throws UregisterException;

    int updateUserInfoForOlympic(OlympicMembers ureg) throws UregisterException;

    int createUserInfoForOlympic(OlympicMembers ureg) throws UregisterException;

    List searchUserInfoForOlympic(OlympicMembers keyword,int startrow,int pagesize,int type) throws UregisterException;

    int searchUserCountForOlympic(OlympicMembers keyword,int type) throws UregisterException;

    List getUserInfoForOlympic(int startrow,int pagesize,int type) throws UregisterException;

    int getUserTotalCountForOlympic(int type) throws UregisterException;

    List getDeptInfoForOlympic() throws UregisterException;

    String getChineseDeptName(String engcode) throws UregisterException;
    //奥运之家项目结束

    //康师傅项目开始
    String getRole_English_NameByID(int roleid) throws UregisterException;
    //康师傅项目结束
}