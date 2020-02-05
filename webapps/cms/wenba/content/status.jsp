<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.wenba.IWenbaManager" %>
<%@ page import="com.bizwink.wenba.wenbaManagerImpl" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=gbk" language="java" %>
<%
    int id=ParamUtil.getIntParameter(request,"id",-1);
    int dpage=ParamUtil.getIntParameter(request,"dpage",-1);
    int status=ParamUtil.getIntParameter(request,"status",-1);
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
   // System.out.println("columnID="+columnID);
    if(id!=-1)
    {
        IWenbaManager columnMgr = wenbaManagerImpl.getInstance();
        int i=0;
        if(status==1)
        {
            List list=columnMgr.getAnswer(id);
            if(list.isEmpty())
            {
                  i=columnMgr.updateQuestionstatus(id,0);
            }
            else{
                  i=columnMgr.updateQuestionstatus(id,1);
            }

        }
        else{
              i=columnMgr.updateQuestionstatus(id,2);
        }
     //   i=columnMgr.updateQuestionstatus(id,2);
        if(i!=0)
         {
             out.write("<script language=javascript>window.location='articles.jsp?dpage="+dpage+"&column="+columnID+"'</script>");
         }
         else
         {
             out.write("<script language=javascript>window.location='articles.jsp?dpage="+dpage+"&column="+columnID+"'</script>");
         }
    }
%>
