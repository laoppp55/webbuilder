<style type="text/css">
TABLE {FONT-SIZE: 12px;word-break:break-all}
BODY {FONT-SIZE: 12px;margin-top: 0px;margin-bottom: 0px; line-height:20px;}
.TITLE {FONT-SIZE:16px; text-align:center; color:#FF0000; font-weight:bold; line-height:30px;}
.FONT01 {FONT-SIZE: 12px; color:#FFFFFF; line-height:20px;}
.FONT02 {FONT-SIZE: 12px; color:#D04407; font-weight:bold; line-height:20px;}
.FONT03 {FONT-SIZE: 14px; color:#000000; line-height:25px;}
A:link {text-decoration:none;line-height:20px;}
A:visited {text-decoration:none;line-height:20px;}
A:active {text-decoration:none;line-height:20px; font-weight:bold;}
A:hover {text-decoration:none;line-height:20px;}
.pad {padding-left:4px; padding-right:4px; padding-top:2px; padding-bottom:2px; line-height:20px;}
.form{border-bottom:#000000 1px solid; background-color:#FFFFFF; border-left:#000000 1px solid; border-right:#000000 1px solid; border-top:#000000 1px solid; font-size: 9pt; font-family:"宋体";}
.botton{border-bottom:#000000 1px solid; background-color:#F1F1F1; border-left:#FFFFFF 1px solid; border-right:#333333 1px solid; border-top:#FFFFFF 1px solid; font-size: 9pt; font-family:"宋体"; height:20px; color: #000000; padding-bottom: 1px; padding-left: 1px; padding-right: 1px; padding-top: 1px; border-style: ridge}
 <link href="/Style/css.css" rel="stylesheet" type="text/css" />
 <link rel=stylesheet type=text/css href="../../styles/global.css">
</style>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.xml.TreatmentXML" %>
<%@ page import="java.util.List" %>
<%@ page import="com.xml.Form" %>
<%@ page import="com.xml.IFormManager" %>
<%@ page import="com.xml.FormPeer" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Iterator" %>
<%@ page contentType="text/html;charset=gbk" %>
<%
     Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
     if (authToken == null)
     {
       response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
       return;
     }
    String baseDir= application.getRealPath("/");
    String sitename=authToken.getSitename();
    int siteid=authToken.getSiteID();
    String xmlname= ParamUtil.getParameter(request,"xmlname");
    System.out.println("xmlname="+xmlname);
    TreatmentXML treatxml=new TreatmentXML();
    List list=treatxml.getForm(baseDir+"\\sites\\"+sitename+"\\_prog\\"+xmlname);






    IFormManager formpeer= FormPeer.getInstance();

    int count = 0;
       int zongpage = 0;
       int recordcount = 5;
       int ipage=0;

          count = formpeer.getTableFormCount(xmlname.substring(0,xmlname.indexOf(".xml")),siteid);

    System.out.println("count="+count);
          String dpage=request.getParameter("dpage");
          zongpage = (count + recordcount - 1) / recordcount;

          try{
                ipage=Integer.parseInt(dpage);
          }catch(Exception e){
               ipage=1;
          }

          if (ipage <= 0) ipage = 1;
          if (ipage >= zongpage) ipage = zongpage;    
    List getlist=formpeer.getTableFormList(xmlname.substring(0,xmlname.indexOf(".xml")),siteid,list,ipage,recordcount);
    System.out.println(""+getlist.size());
    HashMap hashmap=new HashMap();
    String field="";
    String str="";
    String t="";
     out.write("<table width='100%'  bgcolor=\"#FFFFFF\" class=\"css_002\"><tr bgcolor=\"#FFFFFF\" class=\"css_001\">");
    String typestr="";
    String radioname="";
    for(int k=0;k<list.size();k++)
       {
          Form ks=(Form)list.get(k);
          // System.out.println("k="+ks);
            if(!ks.getInputtype().equals("radio"))
            {
                          typestr="";


            }
           if(typestr.indexOf(ks.getColumnname())==-1)
           {
             //  System.out.println("typestr======================="+typestr);
              out.write("<td width='10%'>"+ks.getChinesename()+"</td>");
           }

            if(ks.getInputtype().equals("radio"))
            {
                          typestr=typestr+ks.getColumnname();
                          radioname=radioname+ks.getColumnname();
               // System.out.println("----------------------typestr="+typestr+"------------------------");

            }
       }
    out.write("<td width='5%'>修改</td><td width='5%'>删除</td></tr>");
   
    String g="";
    out.write("");
    typestr="";
    String h="";
    System.out.println("radioname="+radioname);
    for(int i=0;i<getlist.size();i++)
    {

       List rowlist=(List)getlist.get(i) ;
       out.write("<tr bgcolor=\"#FFFFFF\">");
      //  System.out.println(""+getlist.size());
       int id=0;
       for(int j=0;j<rowlist.size();j++)
       {
           String a=(String )rowlist.get(j);
           if(a.indexOf("_")!=-1){
              //System.out.println("aa="+a);
              if(a.indexOf("id_")==-1) {
              g= a.substring(a.indexOf("_")+1)+"  &nbsp;";
              h=a.substring(0,a.indexOf("_"));
                //  System.out.println("hhhh="+h);
              }
               else{
                  id=Integer.parseInt( a.substring(a.indexOf("_")+1));
              }
              if(g.indexOf("[fenhang]")==-1)
              {
                  if(g.indexOf("id_")==-1){
                       if(rowlist.size()-1!=j){

                           if(typestr.indexOf(h)==-1)
                           {
                                System.out.println("typestr==============="+typestr+"   h="+h);
                               out.write("<td width='10%'><font color='#FF0000'>"+g+"</font></td>");
                           }
                           if(radioname.indexOf(h)!=-1){
                           typestr=typestr+h+"_";
                           }

                       }
                  }else{
                    id=Integer.parseInt(g);  
                  }
              }else{
                  
              out.write("</tr><tr bgcolor=\"#FFFFFF\" class=\"css_001\">");
              }
           }
       }
        out.write("<td width='5%'><a href='updateform.jsp?id="+id+"&tablename="+xmlname.substring(0,xmlname.indexOf(".xml"))+"' target='_blank'>修改</td><td width='5%'><a href=deleteform.jsp?id="+id+"&tablename="+xmlname.substring(0,xmlname.indexOf(".xml"))+">删除</a></td></tr>");
        if(!radioname.equals(h))
        {
             typestr="";
        } 
    }
    out.write("</table>");


%>
   <%if(zongpage>1)
                        {
                        %>
			   <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>  <td  align="center" valign="bottom" class="blues">

                   总数<%=count%>&nbsp;&nbsp;&nbsp;&nbsp;   <%=ipage%>/<%=zongpage%>   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  <a href="?dpage=1&xmlname=<%=xmlname%>" class="lian1">首页</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="?dpage=<%=ipage-1%>&xmlname=<%=xmlname%>" class="lian1"  >上一页 </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="?dpage=<%=ipage+1%>&xmlname=<%=xmlname%>" class="lian1"  >下一页</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="?dpage=<%=zongpage%>&xmlname=<%=xmlname%>" class="lian1"  >尾页 </a>

                </td>
            </tr>
            </table>
			  <%}%>