<%@page import="com.bizwink.cms.security.Auth" contentType="text/html;charset=GBK"
        %>
<%@ page import="com.bizwink.cms.sjswsbs.IWsbsManager" %>
<%@ page import="com.bizwink.cms.sjswsbs.Letter" %>
<%@ page import="com.bizwink.cms.sjswsbs.WsbsPeer" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="java.math.BigDecimal" %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int id = ParamUtil.getIntParameter(request, "id", 0);
    Letter letter = new Letter();
    IWsbsManager wsbsMgr = WsbsPeer.getInstance();
    letter = wsbsMgr.getLetterById(id);
    //String department = wsbsMgr.getDepartment(letter.getDeptid().intValue());
    String department=letter.getDepartment();
    String category = wsbsMgr.getCategory(letter.getCategorycode().intValue());
    int statusFlag = letter.getStatusflag().intValue();
    String status="未受理";
    if(statusFlag == -1){
        status = "垃圾邮件";
    }
    if(statusFlag == 1){
        status = "完结";
    }
    int publishflag = letter.getPublishflag().intValue();
    int selectflag = letter.getSelectedflag().intValue();
    int newflag = letter.getNewflag().intValue();

    int startflag = ParamUtil.getIntParameter(request,"startflag",0);
    if(startflag ==1){
        String replycontent = ParamUtil.getParameter(request,"replycontent");
        statusFlag = ParamUtil.getIntParameter(request,"statusFlag",0);
        selectflag =ParamUtil.getIntParameter(request,"selectflag",0);
        newflag =ParamUtil.getIntParameter(request,"newflag",0);
        department=ParamUtil.getParameter(request,"department");

        letter = new Letter();
        letter.setReplycontent(replycontent);
        letter.setStatusflag(BigDecimal.valueOf(statusFlag));
        letter.setSelectedflag(BigDecimal.valueOf(selectflag));
        letter.setNewflag(BigDecimal.valueOf(newflag));
        letter.setDepartment(department);
        wsbsMgr.updateLetter(id,letter);
        response.sendRedirect("index.jsp");
    }


%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>信件内容管理</title>
    <link rel="stylesheet" type="text/css" href="css/xinjianneirong.css">
    <meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
    <script type="text/javascript">
        function createWord(id){
            window.open("createWord.jsp?id="+id);
        }
    </script>
</head>

<body style="text-align: center;">


<div class="about">
    <div class="abouTop">
        <p></p>
        <img src="images/xinjian.png" alt="">
    </div>
    <div class="box">
        <ul>
            <li class="li-1">
                <p>《</p>
                <input type="button" value="返回" onclick="history.go(-1)">

            </li>
            <li class="li-2">

                <!-- 选登信件公开标题 -->
                <p><%=letter.getTitle()%></p>
                <table>
                    <tr>
                        <td align="left">提交时间：<%=letter.getCreatedate()==null?"--":letter.getCreatedate().toString().substring(0,10)%></td>
                        <td align="left">反映类型：<%=category%></td>
                        <td align="left">办理时间：<%=letter.getReplytime()==null?"--":letter.getReplytime().toString().substring(0,10)%></td>
                    </tr>
                    <tr>
                        <%--<td>办理部门：<%=department%></td>--%>
                        <td align="left">办理状态：<%=status%></td>
                        <td align="left">写信人：<%=letter.getLinkman()%>&nbsp;电话：<%=letter.getPhone()%></td>
                        <td align="left">查询码：<%=letter.getSearchmsg()%></td>
                    </tr>

                </table>
            </li>
            <li class="li-3" style="min-height:236px;">
                <p class="p1"><span>信件内容</span></p>
                <!-- 选登信件公开内容 -->
                <p class="p2"><%=letter.getContent()%></p>
            </li>

            <li class="li-4">
                <p>回复内容</p>
            </li>
            <form method="post" action="view.jsp" name="form1">
                <input type="hidden" name="startflag" value="1"/>
                <input type="hidden" name="id" value="<%=id%>"/>

            <li class="li-5">
                <p>
                <textarea name="replycontent" rows="8" cols="120"><%=letter.getReplycontent()==null?"":letter.getReplycontent()%></textarea>
                </p>
            </li>
             <li class="li-4">
                 <p>信件状态</p>
             </li>
             <li class="li-2">
                 <table>
                     <tr>
                         <td align="left">受理状态：
                           <select name="statusFlag">
                               <option value="-1" <%=statusFlag==-1?"selected":""%>>垃圾邮件</option>
                               <option value="0" <%=statusFlag==0?"selected":""%>>未受理</option>
                               <option value="1" <%=statusFlag==1?"selected":""%>>完结</option>
                               <option value="2" <%=statusFlag==2?"selected":""%>>历史信件</option>
                           </select>
                        </td>
                        <td align="left">来信选登：
                            <select name="selectflag">
                                <option value="0" <%=selectflag==0?"selected":""%>>私信回复</option>
                                <option value="1" <%=selectflag==1?"selected":""%>>公开回复</option>
                            </select></td>
                         <td align="left">回复部门：</td>
                         <td align="left"><input type="text" name="department" id="department" value="<%=department==null?"":department%>"></td>
                     </tr>
                <%-- <%
                     if(publishflag == 0){
                 %>
                    <tr>
                        <td>不公开</td>
                    </tr>
                 <%
                     }else {
                 %>

                     <tr>
                         <td>来信选登：
                             <select name="selectflag">
                                  <option value="0" <%=selectflag==0?"selected":""%>>不显示</option>
                                  <option value="1" <%=selectflag==1?"selected":""%>>显示</option>
                             </select>
                         </td>
                         <td>最新来信：
                             <select name="newflag">
                                 <option value="0" <%=newflag==0?"selected":""%>>不显示</option>
                                 <option value="1" <%=newflag==1?"selected":""%>>显示</option>
                             </select>
                         </td>
                     </tr>
                 <%
                     }
                 %>
                    <%if(statusFlag == 1){%>
                     <tr>
                         <td align="left">来信选登：
                             <select name="selectflag">
                                 <option value="0" <%=selectflag==0?"selected":""%>>私信回复</option>
                                 <option value="1" <%=selectflag==1?"selected":""%>>公开回复</option>
                             </select>
                         </td>
                     <%}
                      if(statusFlag != -1){%>
                         <td align="left">最新来信：
                             <select name="newflag">
                                 <option value="0" <%=newflag==0?"selected":""%>>不显示</option>
                                 <option value="1" <%=newflag==1?"selected":""%>>显示</option>
                             </select>
                         </td>
                     </tr>
                     <%}%>--%>
                 </table>
             </li>
               <li class="li-3">
                <table align="center">
                    <tr align="center">
                        <td></td>
                        <td style="text-align:center;padding:5px;">
                            <!--button value="提交" class="DL" onClick="javascript:submit2();">提交</button-->
                            <input type="submit" name="sub" id="sub" value="提交" class="DL"/>
                        </td>
                        <td style="text-align:center;padding:5px;" colspan="2">
                            <a href="createWord.jsp?id=<%=id%>">点击生成文档</a>
                            <%--<button type="button" value="取消" class="ZC" onclick="history.go(-1)">取消</button>--%>
                            <%--<button type="button" value="生成文档" class="ZC" onclick="createWord(<%=id%>)">生成文档</button>--%>
                        </td>
                        <td></td>
                </table>
               </li>
            </form>

        </ul>
    </div>
</div>

</body>
</html>