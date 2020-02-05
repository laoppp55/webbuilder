package com.bizwink.cms.business.Other;

import java.util.*;
import com.bizwink.cms.tree.*;
import com.bizwink.cms.audit.*;
import java.sql.*;

public interface IOtherManager{

    public List getGHList(int startindex,int range,String sql) throws OtherException;

    public void newGH(Other other) throws OtherException;

    public void delGH(int id) throws OtherException;

    public List getAccountList(String simname,int kind,int flag,int startindex,int range) throws OtherException;
    //kind=1 用户名  2用户真名

    public List getReceiveMoneyList(int kind,String what1,String what2,String userid,int startindex,int range) throws OtherException;

    public ReceiveMoney getAReceiveMoney(int orderid) throws OtherException;

    public void newReceiveMoney(ReceiveMoney remoney) throws OtherException;

    public void updateReceiveMoney(ReceiveMoney remoney) throws OtherException;

    // 统计页面使用
    public List getUserRegTimes(long begint,long endt,int kind)throws OtherException;

    public List getOrderNums(long begint,long endt,int kind) throws SQLException;

    public List getCountProduct() throws SQLException;

    public ReceiveMoney getAReceiveMoney(String userid) throws OtherException;

    // 申请页面使用
    public List getApplicationList(int kind,int userid,String username,int startindex,int range) throws OtherException;
    //kind 为: 1 保修申请   2 更换申请   3 退货申请  4 处理完毕,包括批准未批准
    //userid 为0是搜索全部或username查找,有id时为单id搜索
    public void makeDealed(int id,int kind,int status) throws OtherException;
    //kind 为: 1 保修申请批准   2 更换申请批准   3 退货申请批准
    public int getApplicationNum(int kind,int status,int userid,String username)throws OtherException;
    //kind 为: 1 保修申请   2 更换申请   3 退货申请  4 处理完毕,包括批准未批准
    public boolean checkPayMoney(long orderid) throws SQLException;

    public float getPayMoney(long orderid) throws SQLException;
}
