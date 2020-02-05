<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.toolkit.searchword.ISearchWordManager" %>
<%@ page import="com.bizwink.cms.toolkit.searchword.SearchWordPeer" %>
<%@ page import="com.bizwink.cms.toolkit.searchword.SearchWord" %>
<%@page contentType="text/html;charset=utf-8" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
      if (authToken == null)
      {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
      }
    int startflag = ParamUtil.getIntParameter(request, "startflag", 0);
    int id = ParamUtil.getIntParameter(request, "id", 0);
    String cname = ParamUtil.getParameter(request,"cname");
    String ename = ParamUtil.getParameter(request,"ename");
    String sname = ParamUtil.getParameter(request,"sname");
    int hotflag = ParamUtil.getIntParameter(request, "hotflag", 0);
    int tabooflag = ParamUtil.getIntParameter(request, "tabooflag", 0);
    if(tabooflag == 1){
        hotflag =0;
    }

    ISearchWordManager searchWordManager = SearchWordPeer.getInstance();
    SearchWord searchWord = new SearchWord();
    if(startflag == 1){
        searchWordManager = SearchWordPeer.getInstance();
        searchWord = new SearchWord();
        searchWord.setCname(cname);
        searchWord.setEname(ename);
        searchWord.setSname(sname);
        searchWord.setHotflag(hotflag);
        searchWord.setTabooflag(tabooflag);
        searchWordManager.updateSearchWord(searchWord, id);
        response.sendRedirect("index.jsp");
    }
    searchWord = searchWordManager.getSearchWordById(id);

%>
<html>
<head>
<title>修改关键词信息</title>
<meta http-equiv=Content-Type content="text/html; charset=utf-8">
<meta http-equiv="Pragma" content="no-cache">
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
</style>
    <script language="javascript">
        function check(){
            if(document.form.cname.value == ""){
                alert("请输入关键词！");
                return false;
            }
            if(document.form.ename.value == ""){
                alert("请输入关键词拼音！");
                return false;
            }
            if(document.form.sname.value == ""){
                alert("请输入关键词首字母！");
                return false;
            }
            document.form.action = "edit.jsp";
            document.form.submit();
        }


  </script>
</head>
<body>
<form name="form" action="" method="post">
<input type="hidden" name="startflag" value="1">
<input type="hidden" name="id" value="<%=id%>">
<center>
<table width="90%" border="0" cellpadding="4" cellspacing="0" bgcolor="#FFFFFF" class="css_002">
<tr>
      <td align="center">
        <table width="70%" border="0" cellpadding="0">
          <tr bgcolor="#F4F4F4" align="center">
            <td height="30" valign="middle" bgcolor="#F4F4F4" class="css_003">修改关键词信息</td>
          </tr>
          <tr bgcolor="#d4d4d4" align="center">
            <td>
              <table width="100%" border="0" cellpadding="3" cellspacing="1" class="css_002">
                <tr  bgcolor="#FFFFFF" class="css_001">
                  <td width="50%" align="right">关键词：&nbsp;</td>
                    <td align="left" width="50%">&nbsp;<input type="text" name="cname" value="<%=searchWord.getCname()==null?"":searchWord.getCname()%>"></td>
                </tr>
                <tr  bgcolor="#FFFFFF" class="css_001">
                  <td width="50%" align="right">关键词拼音：&nbsp;</td>
                    <td align="left" width="50%">&nbsp;<input type="text" name="ename" value="<%=searchWord.getEname()==null?"":searchWord.getEname()%>"></td>
                </tr>
                <tr  bgcolor="#FFFFFF" class="css_001">
                  <td width="50%" align="right">关键词拼音首字母：&nbsp;</td>
                    <td align="left" width="50%">&nbsp;<input type="text" name="sname" value="<%=searchWord.getSname()==null?"":searchWord.getSname()%>"></td>
                </tr>
                  <tr bgcolor="#FFFFFF" class="css_001">
                      <td width="50%" align="right">是否热搜词：&nbsp;</td>
                      <td align="left" width="50%">&nbsp;
                          <select name="hotflag" id="hotflag">
                              <option value="0" <%if(searchWord.getHotflag()==0){%>selected<%}%>>否</option>
                              <option value="1" <%if(searchWord.getHotflag()==1){%>selected<%}%>>是</option>
                          </select>
                      </td>
                  </tr>
                  <tr bgcolor="#FFFFFF" class="css_001">
                      <td width="50%" align="right">是否敏感词：&nbsp;</td>
                      <td align="left" width="50%">&nbsp;
                          <select name="tabooflag" id="tabooflag">
                              <option value="0" <%if(searchWord.getTabooflag()==0){%>selected<%}%>>否</option>
                              <option value="1" <%if(searchWord.getTabooflag()==1){%>selected<%}%>>是</option>
                          </select>
                      </td>
                  </tr>
               </table>
            </td>
          </tr>
            <tr><td>&nbsp;</td></tr>
            <tr>
                <td align="center">
                    <input type="button" name="ok" value=" 修 改 " onclick="check()">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="button" name="ok" value=" 返 回 " onclick=javascript:history.go(-1);>
                </td>
            </tr>
        </table>
      </td>
</tr>
</table>

</center>
    </form>
</body>
</html>