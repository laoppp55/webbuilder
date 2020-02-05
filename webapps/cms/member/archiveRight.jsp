<%@ page import="java.util.Calendar,
                 java.sql.Timestamp,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.security.*" contentType="text/html;charset=utf-8" %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteid = authToken.getSiteID();
    String errormsg = "";
    boolean error = false;
    IColumnManager columnMgr = ColumnPeer.getInstance();
    boolean doUpdate = ParamUtil.getBooleanParameter(request, "doUpdate");
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    int byear = 0;
    int bmonth = 0;
    int bday = 0;
    int tyear = 0;
    int tmonth = 0;
    int tday = 0;

    if (doUpdate) {
        int achieve = ParamUtil.getIntParameter(request, "achieve", 0);
        int includeSubCol = ParamUtil.getIntParameter(request, "includeSubCol", 0);

        if (achieve == 0) {
            byear = ParamUtil.getIntParameter(request, "year1", 0);
            bmonth = ParamUtil.getIntParameter(request, "month1", 0);
            bday = ParamUtil.getIntParameter(request, "day1", 0);

            if (columnID == 0 || byear == 0 || bmonth == 0 || bday == 0) {
                errormsg = "归档失败！";
                error = true;
            }
        } else if (achieve == 1) {
            byear = ParamUtil.getIntParameter(request, "year2", 0);
            bmonth = ParamUtil.getIntParameter(request, "month2", 0);
            bday = ParamUtil.getIntParameter(request, "day2", 0);
            tyear = ParamUtil.getIntParameter(request, "year3", 0);
            tmonth = ParamUtil.getIntParameter(request, "month3", 0);
            tday = ParamUtil.getIntParameter(request, "day3", 0);

            if (columnID == 0 || byear == 0 || bmonth == 0 || bday == 0 || tyear == 0 || tmonth == 0 || tday == 0) {
                errormsg = "归档失败！";
                error = true;
            }
        }

        if ((!error) && (achieve == 0 || achieve == 1)) {
            Calendar calendar = Calendar.getInstance();
            calendar.clear();
            calendar.set(byear, bmonth - 1, bday);
            Timestamp bdate = new Timestamp(calendar.getTimeInMillis());
            calendar.clear();
            calendar.set(tyear, tmonth - 1, tday);
            Timestamp tdate = new Timestamp(calendar.getTimeInMillis());

            IArticleManager articleMgr = ArticlePeer.getInstance();
            try {
                articleMgr.PigeonholeArticle(columnID, achieve, bdate, tdate, includeSubCol, siteid);
            } catch (ArticleException e) {
                e.printStackTrace();
            }
            errormsg = "归档成功！";
        } else {
            if ((!error) && (achieve == 2 || achieve == 3 || achieve == 4)) {
                IArticleManager articleMgr = ArticlePeer.getInstance();

                try {
                    articleMgr.PigeonholeArticle(columnID, achieve, includeSubCol, siteid);
                } catch (ArticleException e) {
                    e.printStackTrace();
                }
                errormsg = "归档规则设置成功！";
            }
        }
    }

    String cname = "";
    int archivingrules = 0;
    if (columnID > 0) {
        Column column = columnMgr.getColumn(columnID);
        cname = column.getCName();
        cname = StringUtil.gb2iso4View(cname);
        archivingrules = column.getArchivingrules();
    }
%>

<html>
<head>
    <title></title>
    <meta http-equiv=Content-Type content="text/html; charset=utf-8">
    <link rel="stylesheet" type="text/css" href="../style/global.css">
</head>
<%
    String[][] titlebars = {
            {"文章归档", ""},
            {cname, ""}
    };
    String[][] operations = {
            {"系统管理", "javascript:parent.location='index.jsp'"}
    };
%>
<%@ include file="../inc/titlebar.jsp" %>

<BODY>
<br>

<p align=center style="font-size:9pt"><font color=red><%=errormsg%>
</font></p>

<form method="POST" action="archiveRight.jsp?column=<%=columnID%>" onsubmit="javascript:return confirm('真的要进行归档？');">
    <input type=hidden name=doUpdate value=true>
    <table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width='60%'
           align=center>
        <tr bgcolor="#dddddd">
            <td width="100%" height="25">
                <p align="center"><b>文章归档</b></p>
            </td>
        </tr>
        <tr>
            <td>
                <table border="0" cellpadding="10" cellspacing="0" width="100%">
                    <tr width="100%">
                        <td width="100%" height="52">
                            <p align="left"><input type="radio" name="achieve" value="0" <%if(archivingrules == 0){%>checked<%}%>>&nbsp;&nbsp;将&nbsp;<select
                                    size="1" name="year1">
                                <option selected value="2004">2004</option>
                                <option value="2005">2005</option>
                                <option value="2006">2006</option>
                                <option value="2007">2007</option>
                                <option value="2008">2008</option>
                            </select>&nbsp;年&nbsp;<select size="1" name="month1">
                                <option selected value="01">01</option>
                                <option value="02">02</option>
                                <option value="03">03</option>
                                <option value="04">04</option>
                                <option value="05">05</option>
                                <option value="06">06</option>
                                <option value="07">07</option>
                                <option value="08">08</option>
                                <option value="09">09</option>
                                <option value="10">10</option>
                                <option value="11">11</option>
                                <option value="12">12</option>
                            </select>&nbsp;月&nbsp;<select size="1" name="day1">
                                <option selected value="01">01</option>
                                <option value="02">02</option>
                                <option value="03">03</option>
                                <option value="04">04</option>
                                <option value="05">05</option>
                                <option value="06">06</option>
                                <option value="07">07</option>
                                <option value="08">08</option>
                                <option value="09">09</option>
                                <option value="10">10</option>
                                <option value="11">11</option>
                                <option value="12">12</option>
                                <option value="13">13</option>
                                <option value="14">14</option>
                                <option value="15">15</option>
                                <option value="16">16</option>
                                <option value="17">17</option>
                                <option value="18">18</option>
                                <option value="19">19</option>
                                <option value="20">20</option>
                                <option value="21">21</option>
                                <option value="22">22</option>
                                <option value="23">23</option>
                                <option value="24">24</option>
                                <option value="25">25</option>
                                <option value="26">26</option>
                                <option value="27">27</option>
                                <option value="28">28</option>
                                <option value="29">29</option>
                                <option value="30">30</option>
                                <option value="31">31</option>
                            </select>&nbsp;日以前的文章手动进行归档&nbsp;
                            </p>
                        </td>
                    </tr>
                    <tr width="100%">
                        <td width="100%" height="52">
                            <p align="left"><input type="radio" name="achieve" value="1">&nbsp;&nbsp;将&nbsp;<select
                                    size="1" name="year2">
                                <option selected value="2004">2004</option>
                                <option value="2005">2005</option>
                                <option value="2006">2006</option>
                                <option value="2007">2007</option>
                                <option value="2008">2008</option>
                            </select>&nbsp;年&nbsp;<select size="1" name="month2">
                                <option selected value="01">01</option>
                                <option value="02">02</option>
                                <option value="03">03</option>
                                <option value="04">04</option>
                                <option value="05">05</option>
                                <option value="06">06</option>
                                <option value="07">07</option>
                                <option value="08">08</option>
                                <option value="09">09</option>
                                <option value="10">10</option>
                                <option value="11">11</option>
                                <option value="12">12</option>
                            </select>&nbsp;月&nbsp;<select size="1" name="day2">
                                <option selected value="01">01</option>
                                <option value="02">02</option>
                                <option value="03">03</option>
                                <option value="04">04</option>
                                <option value="05">05</option>
                                <option value="06">06</option>
                                <option value="07">07</option>
                                <option value="08">08</option>
                                <option value="09">09</option>
                                <option value="10">10</option>
                                <option value="11">11</option>
                                <option value="12">12</option>
                                <option value="13">13</option>
                                <option value="14">14</option>
                                <option value="15">15</option>
                                <option value="16">16</option>
                                <option value="17">17</option>
                                <option value="18">18</option>
                                <option value="19">19</option>
                                <option value="20">20</option>
                                <option value="21">21</option>
                                <option value="22">22</option>
                                <option value="23">23</option>
                                <option value="24">24</option>
                                <option value="25">25</option>
                                <option value="26">26</option>
                                <option value="27">27</option>
                                <option value="28">28</option>
                                <option value="29">29</option>
                                <option value="30">30</option>
                                <option value="31">31</option>
                            </select>&nbsp;日到<select
                                    size="1" name="year3">
                                <option selected value="2004">2004</option>
                                <option value="2005">2005</option>
                                <option value="2006">2006</option>
                                <option value="2007">2007</option>
                                <option value="2008">2008</option>
                            </select>&nbsp;年&nbsp;<select size="1" name="month3">
                                <option selected value="01">01</option>
                                <option value="02">02</option>
                                <option value="03">03</option>
                                <option value="04">04</option>
                                <option value="05">05</option>
                                <option value="06">06</option>
                                <option value="07">07</option>
                                <option value="08">08</option>
                                <option value="09">09</option>
                                <option value="10">10</option>
                                <option value="11">11</option>
                                <option value="12">12</option>
                            </select>&nbsp;月&nbsp;<select size="1" name="day3">
                                <option selected value="01">01</option>
                                <option value="02">02</option>
                                <option value="03">03</option>
                                <option value="04">04</option>
                                <option value="05">05</option>
                                <option value="06">06</option>
                                <option value="07">07</option>
                                <option value="08">08</option>
                                <option value="09">09</option>
                                <option value="10">10</option>
                                <option value="11">11</option>
                                <option value="12">12</option>
                                <option value="13">13</option>
                                <option value="14">14</option>
                                <option value="15">15</option>
                                <option value="16">16</option>
                                <option value="17">17</option>
                                <option value="18">18</option>
                                <option value="19">19</option>
                                <option value="20">20</option>
                                <option value="21">21</option>
                                <option value="22">22</option>
                                <option value="23">23</option>
                                <option value="24">24</option>
                                <option value="25">25</option>
                                <option value="26">26</option>
                                <option value="27">27</option>
                                <option value="28">28</option>
                                <option value="29">29</option>
                                <option value="30">30</option>
                                <option value="31">31</option>
                            </select>&nbsp;日之间的文章手动进行归档&nbsp;
                            </p>
                        </td>
                    </tr>
                    <tr width="100%">
                        <td width="100%" height="52">
                            <p align="left"><input type="radio" name="achieve" value="2" <%if(archivingrules == 2){%>checked<%}%>>&nbsp;&nbsp;按月自动进行归档
                            </p>
                        </td>
                    </tr>
                    <tr width="100%">
                        <td width="100%" height="52">
                            <p align="left"><input type="radio" name="achieve" value="3" <%if(archivingrules == 3){%>checked<%}%>>&nbsp;&nbsp;按周自动进行归档
                            </p>
                        </td>
                    </tr>
                    <tr width="100%">
                        <td width="100%" height="52">
                            <p align="left"><input type="radio" name="achieve" value="4" <%if(archivingrules == 4){%>checked<%}%>>&nbsp;&nbsp;按日自动进行归档
                            </p>
                        </td>
                    </tr>
                    <tr width="100%">
                        <td align="center"><input type="checkbox" name="includeSubCol" value="1">包含子栏目 <input type="submit"
                                                                                                              value="  提交  "
                                                                                                              name="submit"
                                                                                                              <%if(columnID==0){%>disabled<%}%>>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</form>

</BODY>
</html>
