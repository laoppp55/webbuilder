package com.bizwink.wenba;

import java.util.List;

public interface IWenbaManager {

    void create(wenbaImpl column) throws wenbaException;

    void update(wenbaImpl column) throws wenbaException;

    boolean duplicateEnName(int parentColumnID, String enName) throws wenbaException;

    void remove(int ID, int siteid) throws wenbaException;

    wenbaImpl getColumn(int ID) throws wenbaException;

    List getWenttiList(int ipage,int columnid) ;

    int count(int columnid);

    List getAnswer(int id);

    int deleteAnswer(int id,int qid,String username,int userid);

    String getWenti(int id);

    int updateQuestionstatus(int id,int status);

    int deleteQuestion(int id);

    List getRegister(int ipage,int itype);

    int getCountRegister(int itype);

    int updateRegister(int id,int iemoney );

    int deleteRegister(int id);

    int updateRegisterStatus(int id, int status);

    Register getVip(int id) ;

    int updateVip(int id,Register reg);

    int updateIstop(int id, int istop, int columnid);

    Picture getMediaName(int articleid);

    int insertMedia_Tblpicture( Picture pic);

    int E_addinfo(int usrid,int money,int xinmoney,String beizhu,String rmb,String payway);

    int countSearchRegister(int itype,int searchtype,String keyword);

    List getSearchRegister(int ipage, int itype,int searchtype,String keyword);

    int updateUser_flag(int id,int user_flag);

    List getE_chongzhijilu(int userid);
}
