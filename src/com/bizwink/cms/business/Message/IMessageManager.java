package com.bizwink.cms.business.Message;

import java.util.*;
import com.bizwink.cms.tree.*;
import com.bizwink.cms.audit.*;
import java.sql.*;

public interface IMessageManager{

    public List getMessageList(int flag,int userid,int kind,String search,int siteid,int startindex,int range)throws MessageException;
    //flag为:1后台发;0前台发(供系统区分管理员消息)    userid为-1时为后台管理员;    kind为: 0 收件箱 1发件箱 2 收藏
    // kind为2时是收藏夹列表,此时查询en_savemessage表,列出userid的收藏

    public void updateDeleteFlag(int id,int kind,int siteid)throws MessageException;
    //kind为: 1 收件箱删除  2 发件箱删除    实际上为修改删除标志位
    //kind为: 3 收藏夹删除  删除en_savemessage表内容

    public void newMessage(Message message) throws MessageException;

    public void newSaveMessage(int id,int userid,int siteid) throws MessageException;
}
