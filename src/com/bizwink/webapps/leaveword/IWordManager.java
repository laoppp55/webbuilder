package com.bizwink.webapps.leaveword;

import java.util.*;

public interface IWordManager {

    abstract List getAllWord(String sql) throws WordException;

    abstract void insertWord(Word word) throws WordException;

    abstract void deleteWord(int id) throws WordException;

    abstract List getCurrentWord(int siteid, int formid, int auditflag, int startIndex, int numResult) throws WordException;

    Word getAWord(int id) throws WordException;

    Word getAWordByUserid(String userid,int siteid) throws WordException;

    void updateFlag(int id, int flag);

    List getMassage() throws WordException;

    void updateRetcontent(int id, String retcontent) throws WordException;

    void updateRetcontentForLWManager(int id, String retcontent,String username) throws WordException;

    int getTotalWord(int siteid,int formid,int auditflag) throws WordException;

    List getCurrentWord(String sql,int startIndex, int numResult) throws WordException ;

    void updateDepartmentForLeaveWord(int siteid,int formid,int id,int departmentid,String processor);

    List getWordAuthorizedUser(String userid,int siteid) throws WordException;

    List getValidReson(int siteid) throws WordException;

    boolean setValidForAWord(int siteid,int id,String reason,int validvalue) throws WordException;

    boolean retWordToManager(String userid,int siteid,int lwid,int id,int managertype) throws WordException;

    List getAllCitiaos(int siteid) throws WordException;

    CiTiao getACitiao(int siteid,int id) throws WordException;

    void insertCitiao(CiTiao citiao) throws WordException;

    void updateCitiao(CiTiao citiao) throws WordException;

    void deleteCitiao(int siteid,int id) throws WordException;

    void insertColumn(Column column) throws WordException;

    Column getAColumn(int siteid,int id) throws WordException;

    String getAColumnName(int siteid,int id) throws WordException;

    List getALLColumn(int siteid) throws WordException;

    void updateColumn(Column column) throws WordException;

    void updateColumnForItem(int siteid,int markid,int id,int columnid) throws WordException;

    void deleteColumn(int siteid,int id) throws WordException;

    void writeFinalResult(int id, String retcontent) throws WordException;

    List searchLW(String sdate,String edate,String keyword,int infotype,String deparment,String userid,int startindex,int range,int siteid,int lwid,int usertype) throws WordException;

    List searchLWForWeb(String sdate,String edate,String keyword,int startindex,int range,int siteid,int lwid) throws WordException;

   int getCountsearchLWForWeb(String sdate,String edate,String keyword,int startindex,int range,int siteid,int lwid) throws WordException;

    String exportContentForLWManagerOfDept(int siteid,int markid,String userid,String department,String sitename,String path) throws WordException;

    String exportContentForLWManager(int siteid,int markid,String userid,String sitename,String path) throws WordException;

    int haveAnwserFromEmployee(int id, String processor) throws WordException;

    void updateRetcontentFromDept(int siteid,int formid,int lwid,String userid, String retcontent) throws WordException;

    String getAWordForDept(int siteid, int formid,int lwid,String processor) throws WordException;

    Word getAWordIncludeAllRetContent(int id) throws WordException;
}