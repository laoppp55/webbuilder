<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.joincompany.IJoincompanyManager" %>
<%@ page import="com.bizwink.joincompany.JoincompanyPeer" %>
<%@ page contentType="text/html;charset=gbk" %>
<%
       int id= ParamUtil.getIntParameter(request,"id",-1);
       String dpage=ParamUtil.getParameter(request,"dpage");
       if(id>-1){
           int i=0;
           IJoincompanyManager jpeer= JoincompanyPeer.getInstance();

           i=jpeer.deleteJoincompany(id);
       if (i == 1) {


            out.write("<script type=\"text/javascript\">alert('提交成功');window.location='list.jsp?dpage="+dpage+"'</script>");
        } else {
            out.write("<script type=\"text/javascript\">alert('提交失败');window.location='list.jsp?dpage="+dpage+"'</script>");
        }
       }
%>