<%@ page import="com.bizwink.collectionmgr.*" %>
<%@ page import="com.bizwink.cms.util.*" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    IBasic_AttributesManager ibaMgr = Basic_AttributesPeer.getInstance();
    GlobalConfig globalCfg = ibaMgr.getGlobalConfig();

    String starttime;
    if (globalCfg.getStartTime() != null) {
        starttime = globalCfg.getStartTime().toString();
    } else {
        starttime = null;
    }

    String proxyloginuser = globalCfg.getProxyloginuser();
    String proxyloginpass = globalCfg.getProxyloginpass();
    proxyloginuser = proxyloginuser == null ? "" : proxyloginuser;
    proxyloginpass = proxyloginpass == null ? "" : proxyloginpass;

    String tkeyword = globalCfg.getTkeyword();
    String bkeyword = globalCfg.getBkeyword();
    int tbrelation = globalCfg.getTbrelation();
    /*tkeyword = tkeyword == null ? "" : new String(tkeyword.getBytes("iso8859_1"),"GBK");
    bkeyword = bkeyword == null ? "" : new String(bkeyword.getBytes("iso8859_1"),"GBK");*/
    tkeyword = tkeyword == null ? "" : tkeyword;
    bkeyword = bkeyword == null ? "" : bkeyword;
%>
<html>
<head>
    <META http-equiv=Content-Type content="text/html; charset=gb2312">
    <LINK href="css/manager.css" type=text/css rel=stylesheet>
    <META content="MSHTML 6.00.6000.16525" name=GENERATOR>
    <style type="text/css">
        <!--
        .STYLE1 {
            color: #FF0000
        }

        -->
    </style>
    <script type="text/javascript">
        function checkNull() {
            if (eval("document.configForm.loginflag1.checked")) {
                if (configForm.proxyloginuser.value == "") {
                    alert("请输入代理登录用户名！");
                    configForm.proxyloginuser.focus();
                    return false;
                }
                if (configForm.proxyloginpass.value == "") {
                    alert("请输入代理登录密码！");
                    configForm.proxyloginpass.focus();
                    return false;
                }
            }
        }

        function display(flag) {
            if (flag == 1) {
                loginLayer.style.display = "";
            } else if (flag == 0) {
                loginLayer.style.display = "none";
            }
        }
        function displayneed(flag) {
            if (flag == 1) {
                globalLayer.style.display = "";
            } else if (flag == 0) {
                globalLayer.style.display = "none";
            }
        }
    </script>
</head>

<body>
<form action="doSysConfig.jsp" method="post" onSubmit="return checkNull();" name="configForm">
    <input type="hidden" name="id" value=<%=globalCfg.getId()%>>
<TABLE class=tableBorder cellSpacing=1 cellPadding=3 width="95%" align=center
       border=0>
<tbody>
<TR>
    <TD class=title colSpan=6>高级配置</TD>
</TR>
<TR onmouseover='this.style.backgroundColor="#E0EAF5"'
    onmouseout='this.style.backgroundColor=""' bgColor=#f0f4fa>
    <TD width="15%">开始扫描时间<span class="STYLE1">*</span></TD>
    <TD><label><select name="selectStartTimeYear" id="selectStartTimeYear">
        <%for (int i = 0; i < 10; i++) {%>
        <option value="<%=2006+i%>"><%=2006 + i%>
        </option>
        <%}%>
        <option value="<%=starttime==null?"2006":starttime.substring(0,4)%>" selected>
            <%=starttime == null ? "2006" : starttime.substring(0, 4)%>
        </option>
    </select></label>年
        <label><select name="selectStartTimeMoon" id="selectStartTimeMoon">
            <%for (int i = 1; i < 10; i++) {%>
            <option value="0<%=i%>">0<%=i%>
            </option>
            <%}%>
            <option value="11">11</option>
            <option value="12">12</option>
            <option value="<%=starttime==null?"12":starttime.substring(5,7)%>" selected>
                <%=starttime == null ? "12" : starttime.substring(5, 7)%>
            </option>
        </select></label>月
        <label>
            <select name="selectStartTimeDay" id="selectStartTimeDay">
                <%for (int i = 1; i < 10; i++) {%>
                <option value="0<%=i%>">0<%=i%>
                </option>
                <%}%>
                <%for (int i = 10; i < 32; i++) {%>
                <option value="<%=i%>"><%=i%>
                </option>
                <%}%>
                <option value="<%=starttime==null?"31":starttime.substring(8,10)%>" selected>
                    <%=starttime == null ? "31" : starttime.substring(8, 10)%>
                </option>
            </select>
        </label>日
        <label>
            <select name="selectStartTimeHours" id="selectStartTimeHours">
                <%for (int i = 1; i < 10; i++) {%>
                <option value="0<%=i%>">0<%=i%>
                </option>
                <%}%>
                <%for (int i = 10; i < 24; i++) {%>
                <option value="<%=i%>"><%=i%>
                </option>
                <%}%>
                <option value="<%=starttime==null?"23":starttime.substring(11,13)%>" selected>
                    <%=starttime == null ? "23" : starttime.substring(11, 13)%>
                </option>
            </select></label>时
        <label>
            <select name="selectStartTimeMinutes" id="selectStartTimeMinutes">
                <%for (int i = 1; i < 10; i++) {%>
                <option value="0<%=i%>">0<%=i%>
                </option>
                <%}%>
                <%for (int i = 10; i < 60; i++) {%>
                <option value="<%=i%>"><%=i%>
                </option>
                <%}%>
                <option value="<%=starttime==null?"59":starttime.substring(14,16)%>" selected>
                    <%=starttime == null ? "59" : starttime.substring(14, 16)%>
                </option>
            </select>
        </label>分
    </TD>
</tr>
<TR onmouseover='this.style.backgroundColor="#E0EAF5"'
    onmouseout='this.style.backgroundColor=""' bgColor=#f0f4fa>
    <TD width="20%">扫描时间间隔(小时)<span class="STYLE1">*</span></TD>
    <TD><input name="interval" id="interval" size="8"
               value="<%=(globalCfg.getInterval()==0?12:globalCfg.getInterval())%>"></td>
</tr>
<TR onmouseover='this.style.backgroundColor="#E0EAF5"'
    onmouseout='this.style.backgroundColor=""' bgColor=#f0f4fa>
    <td>是否使用代理</td>
    <td><input name="proxyflag" type="radio" value="0"<%if(globalCfg.getProxyflag()!=1)out.print("checked");%>>否&nbsp;
        <input name="proxyflag" type="radio" value="1"<%if(globalCfg.getProxyflag()==1)out.print("checked");%>>是
    </td>
</tr>
<TR onmouseover='this.style.backgroundColor="#E0EAF5"'
    onmouseout='this.style.backgroundColor=""' bgColor=#f0f4fa>
    <td>代理服务器登录用户名：</td>
    <td><input name="proxyloginuser" id="proxyloginuserid" size="60"
               value="<%=(globalCfg.getProxyloginuser()==null?"":globalCfg.getProxyloginuser())%>"></td>
</tr>
<TR onmouseover='this.style.backgroundColor="#E0EAF5"'
    onmouseout='this.style.backgroundColor=""' bgColor=#f0f4fa>
    <td>端口：</td>
    <td><input name="proxyPort" id="proxyPort" size="8"
               value="<%=(globalCfg.getProxyPort()==null?"":globalCfg.getProxyPort())%>"></td>
</tr>
<TR onmouseover='this.style.backgroundColor="#E0EAF5"'
    onmouseout='this.style.backgroundColor=""' bgColor=#f0f4fa>
    <td>全局关键词：</td>

    <td colspan=5><input type="radio" name="isneed" id="noneed" value="0"
                         onClick="displayneed(0);" <%if(globalCfg.getKeywordflag()==0)out.print("checked");%>>不需要&nbsp;&nbsp;
        <input type="radio" name="isneed" id="need" value="1"
               onClick="displayneed(1);" <%if(globalCfg.getKeywordflag()==1)out.print("checked");%>>需要
    </td>
</tr>

<TR onmouseover='this.style.backgroundColor="#E0EAF5"'
    onmouseout='this.style.backgroundColor=""' bgColor=#f0f4fa>
    <td>代理登录设置：</td>
    <td colspan=5><input type="radio" name="proxyloginflag" id="loginflag0" value="0"
                         onClick="display(0);" <%if(globalCfg.getProxyloginflag()==0)out.print("checked");%>>不需要登录&nbsp;&nbsp;
        <input type="radio" name="proxyloginflag" id="loginflag1" value="1"
               onClick="display(1);" <%if(globalCfg.getProxyloginflag()==1)out.print("checked");%>>需要登录
    </td>
</tr>
</table>
<div id="globalLayer" style="display:<%if(globalCfg.getKeywordflag()==1)out.print("");else{out.print("none");}%>;">
    <table class=tableBorder cellSpacing=1 cellPadding=3 width="95%" align=center border=0>
        <TR onmouseover='this.style.backgroundColor="#E0EAF5"'
            onmouseout='this.style.backgroundColor=""' bgColor=#f0f4fa>
            <td width="20%">标题关键词<span class="STYLE1">*</span></td>
            <td><label><input type="text" name="titlekeyword" id="tkeyword" size="100" value="<%=tkeyword%>"></label>&nbsp;'|'或关系，'+'与关系
            </td>
        </tr>
        <TR onmouseover='this.style.backgroundColor="#E0EAF5"'
            onmouseout='this.style.backgroundColor=""' bgColor=#f0f4fa>
            <td>正文关键词<span class="STYLE1">*</span></td>
            <td><label><input type="text" name="bodykeyword" id="bkeyword" size="100" value="<%=bkeyword%>"></label>&nbsp;'|'或关系，'+'与关系
            </td>
        </tr>
        <TR onmouseover='this.style.backgroundColor="#E0EAF5"'
            onmouseout='this.style.backgroundColor=""' bgColor=#f0f4fa>
            <td height="20">正文和标题的关系</td>
            <td colspan="5"><label>
                <input type="radio" name="tbrelation"
                       value="2" <%if(tbrelation==2)out.print("checked");else{out.print("");}%>>
                或&nbsp;&nbsp;
                <input type="radio" name="tbrelation"
                       value="1" <%if(tbrelation==1)out.print("checked");else{out.print("");}%>>
                与</label></td>
        </tr>
    </table>
</div>
<div id="loginLayer" style="display:<%if(globalCfg.getProxyloginflag()==1)out.print("");else{out.print("none");}%>;">
    <table class=tableBorder cellSpacing=1 cellPadding=3 width="95%" align=center border=0>
        <TR onmouseover='this.style.backgroundColor="#E0EAF5"'
            onmouseout='this.style.backgroundColor=""' bgColor=#f0f4fa>
            <td width="20%">代理登录用户名<span class="STYLE1">*</span></td>
            <td><input type="text" name="proxyloginuser" id="proxyloginuser" size="50" value="<%=proxyloginuser%>"></td>
        </tr>
        <TR onmouseover='this.style.backgroundColor="#E0EAF5"'
            onmouseout='this.style.backgroundColor=""' bgColor=#f0f4fa>
            <td>代理登录密码<span class="STYLE1">*</span></td>
            <td><input type="text" name="proxyloginpass" id="proxyloginpass" size="50" value="<%=proxyloginpass%>"></td>
        </tr>
    </table>
</div>
<TABLE class=tableBorder cellSpacing=1 cellPadding=3 width="95%" align=center
       border=0>
    <tbody>
        <TR onmouseover='this.style.backgroundColor="#E0EAF5"'
            onmouseout='this.style.backgroundColor=""' bgColor=#f0f4fa>
            <td align="center" colspan="2"><label>
                <input type="submit" name="Submit" value=" 保存 ">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="reset" name="Submit2" value=" 重置 ">
            </label></td>
        </tr>
        <tr>
            <td class="title5" colspan="2">&nbsp;</td>
        </tr>
    </tbody>
</table>
</form>
</body>
</html>