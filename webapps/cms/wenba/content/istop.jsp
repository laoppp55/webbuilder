<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.wenba.wenbaManagerImpl" %>
<%@ page import="com.bizwink.wenba.IWenbaManager" %>
<%@ page contentType="text/html;charset=gbk" %>
<html>
<head>

</head>
<body>
<center>
    <%
        int columnid= ParamUtil.getIntParameter(request,"column",-1);
        String dpage=ParamUtil.getParameter(request,"dpage");
        if(columnid!=-1)
        {
            int id=ParamUtil.getIntParameter(request,"id",-1);
            int istop=ParamUtil.getIntParameter(request,"istop",-1);
             IWenbaManager columnMgr = wenbaManagerImpl.getInstance();
             int i=0;
            i=columnMgr.updateIstop(id,istop,columnid);
            if(i==1)
            {
                out.write("<script language=javascript>alert('修改成功');window.location='articles.jsp?dpage="+dpage+"&column="+columnid+"'</script>");
            }else{
                out.write("<script language=javascript>alert('置顶失败,一个栏目最多置顶3篇文章，请修改后在置顶');window.location='articles.jsp?dpage="+dpage+"&column="+columnid+"'</script>");
            }
        }
    %>
</center>

</body>
</html>