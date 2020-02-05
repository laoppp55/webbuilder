<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.wenba.IWenbaManager" %>
<%@ page import="com.bizwink.wenba.wenbaManagerImpl" %>
<%@ page contentType="text/html;charset=gbk" language="java" %>
<%
     int id= ParamUtil.getIntParameter(request,"id",-1);
     int dpage=ParamUtil.getIntParameter(request,"dpage",-1);
     int qid=ParamUtil.getIntParameter(request,"qid",-1);
     String username=ParamUtil.getParameter(request,"username");
     int userid=ParamUtil.getIntParameter(request,"userid",-1);
    if(id!=-1)
    {
         IWenbaManager columnMgr = wenbaManagerImpl.getInstance();
         int i=0;
         i=columnMgr.deleteAnswer(id,qid,username,userid);
         if(i!=0)
         {
             out.write("<script language=javascript>alert('É¾³ý³É¹¦');window.location='answer.jsp?id="+qid+"'</script>");
         }
         else
         {
             out.write("<script language=javascript>alert('É¾³ýÊ§°Ü');window.location='answer.jsp?id="+qid+"'</script>");
         }
    }

%>