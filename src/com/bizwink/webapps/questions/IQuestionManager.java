package com.bizwink.webapps.questions;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.List;

import com.bizwink.webapps.register.*;

public interface IQuestionManager {

    void update(wenbaImpl column) throws wenbaException;

    boolean duplicateEnName(int parentColumnID, String enName) throws wenbaException;

    void remove(int ID, int siteid) throws wenbaException;

    wenbaImpl getColumn(int ID) throws wenbaException;

    public List getCname() throws wenbaException;

    //public void addWenti(wenbaQuiz wenbaquiz)throws wenbaException;

    public void addWenti(wenti wen)throws wenbaException;

    //public List getQuestions(String ID) throws wenbaException;
    //----------------------------------------------------------------
    public List getQuestions(int end,int begin,int stat,int ID) throws wenbaException;

    public List getTop10Questions(int ID,int stats);

    //获取某个分类下面某个地区前10个最新问题,stats=0表示已经回答的问题，stats=1表示未回答的问题
    public List getTop10Questions_difang(int ID,int stats,String pro);

    //获取某个地区下的所有最新问题(分页显示),stats=0表示已经回答的问题，stats=1表示未回答的问题
    public List getProQuestions(int end,int begin,int stats,String prov,String keys,int id);

    //获取某个分类下面某个地区前10个最新问题,答案个数是零
    public List getpro10Questions0Answer(int ID ,String pro);

    public List getAllQuestions0(int ID,int stats);//查询所有未解决问题

    //获取某个地区下的某个分类下所有最新问题的总数
    public List getProQuestionsPagenums(int stat,String pro) throws wenbaException;

    public List getSousuoQuestionsPid(int end,int begin,int ID,int stats,String keys,String pro);

    public List getSousuoQuestionsPid0an(int end,int begin,int ID,String keys,String pro);

    public List getSousuoQuestionsPidnum0an(int ID,String keys,String pro) throws wenbaException;

    public List getSousuoQuestionsPidnum(int ID,int stat,String keys,String pro) throws wenbaException;

    public List getProQuestion0ans(int end,int begin,String prov,String keys,int id);

    public List getProQuestionsPagenum(String pro) throws wenbaException;

    public List getTop10QuestionsSousuo(int ID,int stats,String skey);

    public List getTop10Questions0Answer(int ID);

    public List getTop10Questions0Answer(int ID,String prov);

    public List getTop10Questions0AnswerSousuo(int ID,String sKey);

    public List getTop8QuestionsXuanshang(int ID);

    public List getTop8QuestionsWenti(int ID);

    //public List getTop10Questions0AnswerPro(int ID,String pro);

    public List getConPage(int stat,int ID) throws wenbaException;

    public List get0ansQuestions_num(int ID) throws wenbaException;

    public List get0ansQuestions(int end,int begin,int ID) throws wenbaException;

    public wenti getQuestion(int ID);

    public ResultSet executequery(String sql)throws wenbaException;

    public int executeupdate(String sql)throws wenbaException;

    public wenbaQuiz getQuestionContent(String ID) throws wenbaException;

    public void addanswer(answer answers)throws wenbaException;

    //改变 answernum （提问的答案数量每多一个回答加一）的值
    public void changeQuestion(int id) throws wenbaException ;

    //改变提问的status（0 未解决1 已解决 2被屏蔽）的值
    public void changeQuestionStatus(int id) throws wenbaException;

    //改变提问的ans_status（1 该回答被选为最佳答案）的值
    public void changeAnwStatus(int id) throws wenbaException;

    public void changePinglunNum(int id) throws wenbaException;

    //得到问题回答的分页方法
    public List getAnwserCon(int end,int begin,String ID) throws wenbaException;

    public answer getAnwserCon_zuijia(String ID) throws wenbaException;

    public List getAnwserConnum(int ID) throws wenbaException;

    public int getAnswerNum(String ID);

    public wenbaImpl getCnameId(int ID) throws wenbaException;
    //用于添加投票的相关信息
    public void addVote(vote vot)throws wenbaException;
    //查询投票回答的相关信息
    public vote getVote(int ID) throws wenbaException;

    public answer getOneansid(int ID);

    //提交回答时在用户信息表中记录  （anwnum)
    // public void changeUseranwnum(int userid,int types) throws wenbaException;

    public void changeanwStatus_wenti(int id) throws wenbaException;

    public  List getUserIDList() throws wenbaException;

    public  List getUserhuidasu(int userID) throws wenbaException;

    public  List DateCon();

    public  List getUserAnwNum(int userID,String bedate,String enddate) throws wenbaException;

    public  List getUserAnwNum2(String userID) throws wenbaException;

    public  List getUserNum_month(String userID) throws wenbaException;

    public  List getUserNum_month_Status(String userID) throws wenbaException;

    public void changeUserinfo(String userid) throws wenbaException;

    public void Pageview(int id) throws wenbaException;

    public List getQuestionsdianji(int ID);

    public List getQuestions_pic(int ID);

    public void changuserinfo_grade(int userid) throws wenbaException;

    public List getzhuangjian();

    public void changbestAans(int num,int userid) throws wenbaException;

    public void changuser_xuanshang(int nums,int userid) throws wenbaException;

    public Uregister jifen(int userID) throws wenbaException;

    public void change_huida(int userid) throws wenbaException;

    public List Cms_con(String Keys,String culumnid);

    public List Cms_select_to(int end_no,int begin_no, String Keys,String culumnid);

    public List Cms_con_wenba(String Keys);

    public List Cms_select_wenba(int end_no,int begin_no, String Keys);

    public List Cms_yanwen_con(String Keys,int cid);

    public List Cms_yawen_select(int end_no,int begin_no, String Keys,int cid);

    public List anszj_5(int userid);

    public List Cms_qw_con(String Keys);

    public List Cms_qw_select(int end_no,int begin_no, String Keys);

    public List getWenti()throws wenbaException;

    public List getZuiXinTiWen()throws wenbaException;

    public List getLingHuiDa()throws wenbaException;

    public List getyijiejue()throws wenbaException;

    public String getCity(int provinceid)throws wenbaException;

    public List getProvince()throws wenbaException;

    public List getLHD(int fenlei,String province,String keyword)throws wenbaException;

    public List getYJJ(int fenlei,String province,String keyword)throws wenbaException;

    public List getZXTW(int fenlei,String province,String keyword)throws wenbaException;

    public List getWenti(String fenlei,String province,String keyword)throws wenbaException;

    public List getyijiejue(int fenlei,String province,String keyword)throws wenbaException;

    public List getZuiXinTiWen(int fenlei,String province,String keyword)throws wenbaException;

    public List getLingHuiDa(int fenlei,String province,String keyword)throws wenbaException;

    public Uregister getzhuanjiiainfo(int userid)throws wenbaException;

    public int setintroduct(int userid,String content)throws wenbaException;

    public List getquestion(int userid)throws wenbaException;

    public List getdifangLHD(String proname,int cid,String keyword)throws wenbaException;

    public List getdifangYJJ(String proname,int cid,String keyword)throws wenbaException;

    public List getdifangZXTW(String proname,int cid,String keyword)throws wenbaException;
    public int edituserimg(int userid,String imgpath,String imgname)throws wenbaException;
    public List getTOP8DianJiShu(int ID)throws wenbaException;
    public int weekgrade(int userid,int grade)throws wenbaException;
    public List getTop8weekgrade()throws wenbaException;
    public int answaddgrade(int userid,int grade)throws wenbaException;
    public int wentiguoqi(int questionid)throws wenbaException;
    public List getpersonwenti(int userid)throws wenbaException;
    public List getpersonanwser(int userid)throws wenbaException;
    public void setzhuanjia()throws wenbaException;
}
