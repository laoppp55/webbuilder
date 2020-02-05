<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.xml.IFormManager" %>
<%@ page import="com.xml.FormPeer" %>
<%@ page import="com.xml.TreatmentXML" %>
<%@ page import="java.util.List" %>
<%@ page import="com.xml.Form" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page contentType="text/html;charset=gbk" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
     if (authToken == null)
     {
       response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
       return;
     }
    String sitename=authToken.getSitename();
    int id= ParamUtil.getIntParameter(request,"id",-1);
    String tablename=ParamUtil.getParameter(request,"tablename");
    TreatmentXML treatxml=new TreatmentXML();
    String baseDir = application.getRealPath("/");
    List list=treatxml.getForm(baseDir+"\\sites\\"+sitename+"\\_prog\\"+tablename+".xml");
     IFormManager formpeer= FormPeer.getInstance();

    List getonelist=formpeer.getOneTableFormresult(id,tablename,list);
    if(id>-1)
    {

        int flag=ParamUtil.getIntParameter(request,"flag",-1);
        if(flag!=-1){
           List updatelist=new ArrayList();
           for(int i=0;i<list.size();i++)
           {
               Form form=(Form)list.get(i);
               String paramvalue=ParamUtil.getParameter(request,form.getColumnname());
               updatelist.add(paramvalue);
           }
           formpeer.updateTableForm(id,tablename,list,updatelist);
            for(int i=0;i<updatelist.size();i++)
           {
               String updateparam=(String)updatelist.get(i);
               System.out.println(""+updateparam);
           }
        }

    }
%>
<html>
<head></head>
    <body>
    <form action="updateform.jsp">
        <input type="hidden" value="1" name="flag">
        <input type="hidden" value="<%=tablename%>" name="tablename">
        <input type="hidden" value="<%=id%>" name="id">
    <table width="500" align="center">
           <%
               if(getonelist.size()-1==list.size()) {
                   String typestr="";
              for(int i=0;i<list.size();i++)
              {
                  Form form=(Form)list.get(i);
                  String columnname=form.getColumnname();
                  String chinesename=form.getChinesename();
                  String columnvalue=(String)getonelist.get(i);

                  if(!form.getInputtype().equals("radio"))
                   {
                     typestr="";
                   }

                   if(typestr.indexOf("radio")==-1){
                  %>
             <tr><td align="right" width="40%"><%=chinesename%>: </td>
             <td><%if(form.getInputtype().equals("radio")){%> <input type="radio" checked="checked" value="<%=columnvalue.substring(columnvalue.indexOf("_")+1)%>" name="<%=columnname%>"><%=columnvalue.substring(columnvalue.indexOf("_")+1)%><%}else{%><input type="<%=form.getInputtype()%>" value="<%=columnvalue.substring(columnvalue.indexOf("_")+1)%>" name="<%=columnname%>"><%}%></td></tr>

           <% }
                 if(form.getInputtype().equals("radio"))
                   {
                     typestr=typestr+"_radio";
                   }
                   }
           }
           %>
    <tr><td width="20%">&nbsp; </td><td><input type="submit" value="提交"></td></tr>  </table> </form>
    </body>
</html>