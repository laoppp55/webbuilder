package com.xml;

import com.bizwink.webapps.leaveword.WordException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-1-11
 * Time: 12:54:29
 * To change this template use File | Settings | File Templates.
 */
public interface IFormManager {
    public int createTable(List list);
    public int insertForm(List list,String tablename,int siteid,int templateid,List listform);
    public List getFileXML(String root);
    public List getTableFormList(String tablename,int siteid, List columnlist,int ipage,int onepagenum);
    public int updateTableForm(int id,String tablename,List listform,List updatelist);
    public int deleteTableForm(int id,String tablename);
    public List getOneTableFormresult(int id,String tablename,List listform);
    public int  getTableFormCount(String tablename,int siteid);
    public boolean insertZhengshu(formfields ff) throws WordException;
    public List getBookList(int start, int range,String sql);
    public void deleteBook(int id);
    public int getBookCount(String sql);

    //无线电干扰申诉表
    List getAllGanRao();
    List getCurrentGanRaoList(int startrow, int range);
    List getCurrentQureyGanRaoList(String sqlstr, int startrow, int range);
    int getAllGanRaoNum();
    int insertGanRao(Form form);
    Form getByIdganrao(int id);
    void updateGanRao(Form form, int id);
    void deleteGanRao(int id);

    //设置无线电台申报表
    List getAllDuiJiangJi();
    List getCurrentDuiJiangJiList(int startrow, int range);
    List getCurrentQureyDuiJiangJiList(String sqlstr, int startrow, int range);
    int getAllDuiJiangJiNum();
    int insertDuiJiangJi(Form form);
    Form getByIdduijiangji(int id);
    void updateDuiJiangJi(Form form, int id);
    void deleteDuiJiangJi(int id);

    List getAllZiliao();
    List getCurrentZiLiaoList(int startrow, int range);
    List getCurrentQureyZiLiaoList(String sqlstr, int startrow, int range);
    int getAllZiLiaoNum();
    int insertZiliao(Form form,List list);
    int insertZiliao(Form form);
    Form getByIdziliao(int id);
    void updateZiliao(Form form, int id);
    void deleteZiliao(int id);

    //转换ACCESS数据库的信息进入到系统数据库
    int existFilename(String filename);
}
