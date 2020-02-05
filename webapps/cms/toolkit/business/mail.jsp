<%@ page import="java.util.*,
  		 com.bizwink.server.*,
  		 com.bizwink.security.*,
  		 com.bizwink.register.*,
  		 com.bizwink.util.*"
  		 contentType="text/html;charset=gbk"
%>

<%
	//IRegisterManager regMgr = RegisterPeer.getInstance();
	//regMgr.SendMail();

    String mailbody = "<meta http-equiv=Content-Type content=text/html; charset=gb2312>"+
                      "<div align=center><a href=http://www.csdn.net> csdn </a></div>";

    SendMail themail = new SendMail("smtp.163.net");
    themail.setNeedAuth(true);

    if(themail.setSubject("标题") == false) return;
    if(themail.setBody(mailbody) == false) return;
    if(themail.setTo("zhangyouyuan@163.net") == false) return;
    if(themail.setFrom("bet@163.net") == false) return;
    if(themail.addFileAffix("c:\\boot.ini") == false) return;
    themail.setNamePass("bet","7zhang8");

    if(themail.sendout() == false) return;

%>