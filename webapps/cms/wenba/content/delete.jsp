<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.wenba.IWenbaManager" %>
<%@ page import="com.bizwink.wenba.wenbaManagerImpl" %>
<%@ page contentType="text/html;charset=gbk" language="java" %>
<%
     int id= ParamUtil.getIntParameter(request,"id",-1);
     int dpage=ParamUtil.getIntParameter(request,"dpage",-1);
     int columnID = ParamUtil.getIntParameter(request, "column", 0);
    if(id!=-1)
    {
         IWenbaManager columnMgr = wenbaManagerImpl.getInstance();
         int i=0;
         i=columnMgr.deleteQuestion(id);
         if(i!=0)
         {
             out.write("<script language=javascript>alert('É¾³ý³É¹¦');window.location='articles.jsp?dpage="+dpage+"&column="+columnID+"'</script>");
         }
         else
         {
             out.write("<script language=javascript>alert('É¾³ýÊ§°Ü');window.location='articles.jsp?dpage="+dpage+"&column="+columnID+"'</script>");
         }
    }

%>