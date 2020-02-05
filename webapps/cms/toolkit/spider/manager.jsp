<%@ page import="com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=gbk"
%>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("/webbuilder/login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
%>
<html>
<head>
<link href="css/manager.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
    function pr()
    {
        var L = document.getElementById("left");
        var R = document.getElementById("right");
        if (L.className == "left")
        {
            L.className = "left1";
            R.className = " ";
        }
        else
        {
            L.className = "left";
            R.className = "right";
        }
    }
</script>
<script typt="text/javascript">
    function GetMenu(ID) {
        var MenuID = document.getElementById("Menu" + ID);
        var ParentDiv = document.getElementById("tabsJ");
        for (var i = 0; i < ParentDiv.getElementsByTagName("li").length; i ++) {
            ParentDiv.getElementsByTagName("li")[i].className = "";
        }
        document.getElementById("Menu" + ID).className = "current";
    }
</script>
<link href="style.jsp" rel="stylesheet" type="text/css">
<style type="text/css">
    Body {
        Background-Image: Url( "images / Bg_Left . gif" );
        Background-Attachment: Fixed;
        Background-Repeat: Repeat-Y;
        Background-Color: #BBC8DE;
        Margin: 0px;
        Line-height: 18px;
        Font-Family: Tahoma, Verdana, Arial, Helvetica, Sans-Serif;
        Font-Size: 12px;
        Color: #000000;
    }

    td {
        font: normal 12px Verdana;
    }

    img {
        vertical-align: bottom;
        border: 0px;
    }

    a {
        font: normal 12px Verdana;
        color: #000000;
        text-decoration: none;
    }

    a:hover {
        color: #FF6600;
        text-decoration: none;
    }

    .sec_menu {
    }

    .menu_title {
    }

    .menu_title span {
        position: relative;
        top: 2px;
        left: 12px;
        color: #253958;
        font-weight: bold;
    }

    .menu_title2 {
    }

    .menu_title2 span {
        position: relative;
        top: 2px;
        left: 12px;
        color: #FFFFFF;
        font-weight: bold;
    }

    .menu {
        height: 22px;
        line-height: 20px;
        border: 1px solid #BACADE;
    }

    /*background-image: url(images/menu_bg.gif);*/
    .menu_2 {
        height: 22px;
        line-height: 20px;
        background-color: #FEFEFE;
        border: 1px solid #748FB3;
    }

    .MenuTree {
        Margin-Left: 5px;
    }

    .Bottomtable {
        Margin-Top: 10px;
        Margin-Left: 6px;
    }

    .menu1 {
        height: 22px;
        line-height: 20px;
        border: 1px solid #EBEAEE;
    }
</style>
<!-- base --></head>

<body style="overflow-y: hidden;">
<div id="header">
    <table class="Toptable" align="right" border="0" cellpadding="0" cellspacing="0" width="100%">
        <tbody>
            <tr>
                <td style="padding-right: 30px;" align="center" width="200" valign="top">
                    <font size=5><strong>信息采集系统</strong></font></td>
                <td id="a1" style="padding-right: 30px;" align="left" width="60"><a href="javascript:pr()">显示/隐藏</a>
                </td>
                <td>您好：<%=authToken.getUserID()%></td>
                <td width="400"></td>
                <td>
                    <div id="tabsJ">
                        <ul>
                            <li id="Menu1"><!--a href="http://www.bizwink.com.cn" target="_blank"
                                              onclick="GetMenu(1);"><span><strong>盈商软件</strong></span></a--></li>
                            <li id="Menu2"><a href="javascript:window.close();" target="_parent" onClick="GetMenu(2);"><span><strong>返回</strong></span></a>
                            </li>
                        </ul>
                    </div>
                </td>
                <td id="a2" align="left">&nbsp;</td>
            </tr>
        </tbody>
    </table>
</div>

<div id="main">
<div id="left" class="left">
<br>
<table class="MenuTree" _base_target="MainFrame" border="0" cellpadding="0" cellspacing="0" width="158">
    <tbody _base_target="MainFrame">
        <tr _base_target="MainFrame">
            <td class="menu_title" onMouseOver="this.className='menu_title2'" onMouseOut="this.className='menu_title'"
                id="menuTitle0" onClick="showmenu_item(0)" style="cursor: pointer;" _base_target="MainFrame"
                background="images/title_bg_show.gif" height="25"><span>系统管理</span>
            </td>
        </tr>
        <tr _base_target="MainFrame">
            <td style="display: none;" id="menu_item0" _base_target="MainFrame">
                <table _base_target="MainFrame" align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tbody _base_target="MainFrame">
                        <tr _base_target="MainFrame">
                            <td _base_target="MainFrame" height="4"></td>
                        </tr>
                        <tr _base_target="MainFrame">
                            <td class="menu" onMouseOver="this.className='menu_2'" onMouseOut="this.className='menu'"
                                _base_target="MainFrame">&nbsp;<font face="Webdings">4</font>&nbsp;<a
                                    href="system/autoSpider.jsp" target="Main"
                                    _base_target="MainFrame">事务管理</a></td>
                        </tr>
                        <tr _base_target="MainFrame">
                            <td class="menu" onMouseOver="this.className='menu_2'" onMouseOut="this.className='menu'"
                                _base_target="MainFrame">&nbsp;<font face="Webdings">4</font>&nbsp;<a
                                    href="system/index.jsp" target="Main"
                                    _base_target="MainFrame">系统状态</a></td>
                        </tr>
                    </tbody>
                </table>
            </td>
        </tr>
    </tbody>
</table>

<table class="MenuTree" _base_target="MainFrame" border="0" cellpadding="0" cellspacing="0" width="158">
    <tbody _base_target="MainFrame">
        <tr _base_target="MainFrame">
            <td class="menu_title" onMouseOver="this.className='menu_title2'" onMouseOut="this.className='menu_title'"
                id="menuTitle1" onClick="showmenu_item(1)" style="cursor: pointer;" _base_target="MainFrame"
                background="images/title_bg_show.gif" height="25"><span>采集管理</span>
            </td>
        </tr>
        <tr _base_target="MainFrame">
            <td style="display: none;" id="menu_item1" _base_target="MainFrame">
                <table _base_target="MainFrame" align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tbody _base_target="MainFrame">
                        <tr _base_target="MainFrame">
                            <td _base_target="MainFrame" height="4"></td>
                        </tr>
												<tr _base_target="MainFrame">
                            <td class="menu1" onMouseOver="this.className='menu_2'" onMouseOut="this.className='menu'"
                                _base_target="MainFrame">&nbsp;<font face="Webdings">4</font>&nbsp;<a
                                    href="sysConfig.jsp" target="Main"
                                    _base_target="MainFrame">系统设置</a></td>
                        </tr>
                        <tr _base_target="MainFrame">
                            <td class="menu1" onMouseOver="this.className='menu_2'" onMouseOut="this.className='menu'"
                                _base_target="MainFrame">&nbsp;<font face="Webdings">4</font>&nbsp;<a
                                    href="index.jsp" target="Main"
                                    _base_target="MainFrame">采集定义</a></td>
                        </tr>
                    </tbody>
                </table>
            </td>
        </tr>
    </tbody>
</table>

<script language="javascript1.2" _base_target="MainFrame">
    function showmenu_item(sid)
    {
        which = eval('menu_item' + sid);
        if (which.style.display == 'none')
        {
            var j = 0
            while (j < 2) {
                eval('menu_item' + j + '.style.display=\'none\';');
                eval('menuTitle' + j + '.background=\'images/title_bg_show.gif\';');
                j++;
            }
            eval('menu_item' + sid + '.style.display=\'\';');
            eval('menuTitle' + sid + '.background=\'images/title_bg_hide.gif\';');
        } else {
            eval('menu_item' + sid + '.style.display=\'none\';');
            eval('menuTitle' + sid + '.background=\'images/title_bg_show.gif\';');
        }
    }
</script>
</div>

<div id="right" class="right">
    <iframe name="Main" src="index.jsp" frameborder="0" height="500" scrolling="auto"
            width="100%"></iframe>
</div>
</div>

<div id="boot"></div>
</body>
</html>