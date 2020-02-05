<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.bizwink.util.pub.*"%>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="java.net.URLEncoder" %>
<%
    URLEncoder.encode("北京", "utf-8");
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect("error.jsp?msgno=-10");
    }

    String userid = authToken.getUserID();
    if (!userid.equals("admin")) {
        response.sendRedirect("ifScheduleLogin.jsp?msgno=-10");
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <base href="<%=basePath%>">
    <title>读取数据接口调度执行页面，请勿关闭！</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<script language="javascript">
	  function doSubmit(act, itf) {
		document.body.style.cursor = "wait";
		var btnAll = document.body.getElementsByTagName("input");
        for(var i=0;i<btnAll.length;i++) {
          btnAll[i].style.cursor = "wait";
        }
	    document.formAct.actType.value = act;
	    document.formAct.itfType.value = itf;
	    document.formAct.submit();
	  }
	</script>
  </head>
  <body>
  <form name ="formAct" action="itfReadSchedule" method="post">
  <input type=hidden name="itfType" value=""/>
  <input type=hidden name="actType" value=""/>
  <input type=hidden name="fromLogin" value="<%=StrUtil.toStr(request.getAttribute("fromLogin"))%>" />
  <table border=0 style="font:宋体;font-size:11pt;">
    <!--tr><td colspan=3 align=right style="border-bottom:2px solid black;"><input type="button" value="全部启动" onclick='doSubmit("start", "<%=ItfConsts.TYPE_ALL%>")'/><input type="button" value="全部停止" onclick='doSubmit("stop", "<%=ItfConsts.TYPE_ALL%>")'/><input type="button" value="全部重启" onclick='doSubmit("restart", "<%=ItfConsts.TYPE_ALL%>")'/></td>
        <td style="border-bottom:2px solid black;padding-left:6px;"><%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_ALL))%></td>
    </tr>
    <tr><td colspan=4 height=30><b>电子商务读取物装ERP接口数据</b></td></tr>
    <tr><td>1、</td>
        <td align="right">ERP调拨单销售发票信息上传</td>
        <td><input type="button" value="启动" onclick='doSubmit("start", "<%=ItfConsts.TYPE_FP%>")'/><input type="button" value="停止" onclick='doSubmit("stop", "<%=ItfConsts.TYPE_FP%>")'/><input type="button" value="重启" onclick='doSubmit("restart", "<%=ItfConsts.TYPE_FP%>")'/></td>
        <td style="padding-left:6px;"><%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_FP))%><input type="hidden" name="info<%=ItfConsts.TYPE_FP%>" value=<%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_FP))%>></td>
    </tr>
    <tr><td>2、</td>
        <td align="right">ERP合同已付款信息上传</td>
        <td><input type="button" value="启动" onclick='doSubmit("start", "<%=ItfConsts.TYPE_FK%>")'/><input type="button" value="停止" onclick='doSubmit("stop", "<%=ItfConsts.TYPE_FK%>")'/><input type="button" value="重启" onclick='doSubmit("restart", "<%=ItfConsts.TYPE_FK%>")'/></td>
        <td style="padding-left:6px;"><%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_FK))%><input type="hidden" name="info<%=ItfConsts.TYPE_FK%>" value=<%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_FK))%>></td>
    </tr>
    <tr><td>3、</td>
        <td align="right">ERP合同已收票信息上传</td>
        <td><input type="button" value="启动" onclick='doSubmit("start", "<%=ItfConsts.TYPE_SP%>")'/><input type="button" value="停止" onclick='doSubmit("stop", "<%=ItfConsts.TYPE_SP%>")'/><input type="button" value="重启" onclick='doSubmit("restart", "<%=ItfConsts.TYPE_SP%>")'/></td>
        <td style="padding-left:6px;"><%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_SP))%><input type="hidden" name="info<%=ItfConsts.TYPE_SP%>" value=<%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_SP))%>></td>
    </tr>
    <tr><td>4、</td>
        <td align="right">ERP合同签订信息上传</td>
        <td><input type="button" value="启动" onclick='doSubmit("start", "<%=ItfConsts.TYPE_HT%>")'/><input type="button" value="停止" onclick='doSubmit("stop", "<%=ItfConsts.TYPE_HT%>")'/><input type="button" value="重启" onclick='doSubmit("restart", "<%=ItfConsts.TYPE_HT%>")'/></td>
        <td style="padding-left:6px;"><%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_HT))%><input type="hidden" name="info<%=ItfConsts.TYPE_HT%>" value=<%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_HT))%>></td>
    </tr>
    <tr><td>5、</td>
        <td align="right">ERP合同已关闭信息上传</td>
        <td><input type="button" value="启动" onclick='doSubmit("start", "<%=ItfConsts.TYPE_HTGB%>")'/><input type="button" value="停止" onclick='doSubmit("stop", "<%=ItfConsts.TYPE_HTGB%>")'/><input type="button" value="重启" onclick='doSubmit("restart", "<%=ItfConsts.TYPE_HTGB%>")'/></td>
        <td style="padding-left:6px;"><%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_HTGB))%><input type="hidden" name="info<%=ItfConsts.TYPE_HTGB%>" value=<%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_HTGB))%>></td>
    </tr>
    <tr><td>6、</td>
        <td align="right">ERP内向和外向交货单上传</td>
        <td><input type="button" value="启动" onclick='doSubmit("start", "<%=ItfConsts.TYPE_JHD%>")'/><input type="button" value="停止" onclick='doSubmit("stop", "<%=ItfConsts.TYPE_JHD%>")'/><input type="button" value="重启" onclick='doSubmit("restart", "<%=ItfConsts.TYPE_JHD%>")'/></td>
        <td style="padding-left:6px;"><%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_JHD))%><input type="hidden" name="info<%=ItfConsts.TYPE_JHD%>" value=<%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_JHD))%>></td>
    </tr>
    <tr><td colspan=4 height=30><b>电子商务读取企业ERP接口数据</b></td></tr>
    <tr><td>1、</td>
        <td align="right">ERP采购申请(PR)上传</td>
        <td><input type="button" value="启动" onclick='doSubmit("start", "<%=ItfConsts.TYPE_PR%>")'/><input type="button" value="停止" onclick='doSubmit("stop", "<%=ItfConsts.TYPE_PR%>")'/><input type="button" value="重启" onclick='doSubmit("restart", "<%=ItfConsts.TYPE_PR%>")'/></td>
        <td style="padding-left:6px;"><%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_PR))%><input type="hidden" name="info<%=ItfConsts.TYPE_PR%>" value=<%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_PR))%>></td>
    </tr>
    <tr><td>2、</td>
        <td align="right">ERP采购订单(PO)上传</td>
        <td><input type="button" value="启动" onclick='doSubmit("start", "<%=ItfConsts.TYPE_PO%>")'/><input type="button" value="停止" onclick='doSubmit("stop", "<%=ItfConsts.TYPE_PO%>")'/><input type="button" value="重启" onclick='doSubmit("restart", "<%=ItfConsts.TYPE_PO%>")'/></td>
        <td style="padding-left:6px;"><%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_PO))%><input type="hidden" name="info<%=ItfConsts.TYPE_PO%>" value=<%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_PO))%>></td>
    </tr>
    <tr><td>3、</td>
        <td align="right">ERP寄售（计划协议、基础油）上传</td>
        <td><input type="button" value="启动" onclick='doSubmit("start", "<%=ItfConsts.TYPE_JS%>")'/><input type="button" value="停止" onclick='doSubmit("stop", "<%=ItfConsts.TYPE_JS%>")'/><input type="button" value="重启" onclick='doSubmit("restart", "<%=ItfConsts.TYPE_JS%>")'/></td>
        <td style="padding-left:6px;"><%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_JS))%><input type="hidden" name="info<%=ItfConsts.TYPE_JS%>" value=<%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_JS))%>></td>
    </tr>
    <tr><td>4、</td>
        <td align="right">ERP供应商评估上传</td>
        <td><input type="button" value="启动" onclick='doSubmit("start", "<%=ItfConsts.TYPE_GYSPG%>")'/><input type="button" value="停止" onclick='doSubmit("stop", "<%=ItfConsts.TYPE_GYSPG%>")'/><input type="button" value="重启" onclick='doSubmit("restart", "<%=ItfConsts.TYPE_GYSPG%>")'/></td>
        <td style="padding-left:6px;"><%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_GYSPG))%><input type="hidden" name="info<%=ItfConsts.TYPE_GYSPG%>" value=<%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_GYSPG))%>></td>
    </tr>
    <tr><td colspan=4 height=30><b>电子商务读取企业IPMS接口数据</b></td></tr>
    <tr><td>1、</td>
        <td align="right">IPMS采购计划上传</td>
        <td><input type="button" value="启动" onclick='doSubmit("start", "<%=ItfConsts.TYPE_PR_Imps%>")'/><input type="button" value="停止" onclick='doSubmit("stop", "<%=ItfConsts.TYPE_PR_Imps%>")'/><input type="button" value="重启" onclick='doSubmit("restart", "<%=ItfConsts.TYPE_PR_Imps%>")'/></td>
        <td style="padding-left:6px;"><%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_PR_Imps))%><input type="hidden" name="info<%=ItfConsts.TYPE_PR_Imps%>" value=<%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_PR_Imps))%>></td>
    </tr>
    <tr><td>2、</td>
        <td align="right">IPMS合同评分上传</td>
        <td><input type="button" value="启动" onclick='doSubmit("start", "<%=ItfConsts.TYPE_Grade%>")'/><input type="button" value="停止" onclick='doSubmit("stop", "<%=ItfConsts.TYPE_Grade%>")'/><input type="button" value="重启" onclick='doSubmit("restart", "<%=ItfConsts.TYPE_Grade%>")'/></td>
        <td style="padding-left:6px;"><%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_Grade))%><input type="hidden" name="info<%=ItfConsts.TYPE_Grade%>" value=<%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_Grade))%>></td>
    </tr>
    <tr><td>3、</td>
        <td align="right">IPMS合同信息上传</td>
        <td><input type="button" value="启动" onclick='doSubmit("start", "<%=ItfConsts.TYPE_PurchaseOrder%>")'/><input type="button" value="停止" onclick='doSubmit("stop", "<%=ItfConsts.TYPE_PurchaseOrder%>")'/><input type="button" value="重启" onclick='doSubmit("restart", "<%=ItfConsts.TYPE_PurchaseOrder%>")'/></td>
        <td style="padding-left:6px;"><%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_PurchaseOrder))%><input type="hidden" name="info<%=ItfConsts.TYPE_PurchaseOrder%>" value=<%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_PurchaseOrder))%>></td>
    </tr>
    <tr><td colspan=4 height=30><b>电子商务读取MDM接口数据</b></td></tr>
    <tr><td>1、</td>
        <td align="right">物料分类同步</td>
        <td><input type="button" value="启动" onclick='doSubmit("start", "<%=ItfConsts.TYPE_PRODCLASS%>")'/><input type="button" value="停止" onclick='doSubmit("stop", "<%=ItfConsts.TYPE_PRODCLASS%>")'/><input type="button" value="重启" onclick='doSubmit("restart", "<%=ItfConsts.TYPE_PRODCLASS%>")'/></td>
        <td style="padding-left:6px;"><%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_PRODCLASS))%><input type="hidden" name="info<%=ItfConsts.TYPE_PRODCLASS%>" value=<%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_PRODCLASS))%>></td>
    </tr>
    <tr><td>2、</td>
        <td align="right">物料明细同步</td>
        <td><input type="button" value="启动" onclick='doSubmit("start", "<%=ItfConsts.TYPE_PROD%>")'/><input type="button" value="停止" onclick='doSubmit("stop", "<%=ItfConsts.TYPE_PROD%>")'/><input type="button" value="重启" onclick='doSubmit("restart", "<%=ItfConsts.TYPE_PROD%>")'/></td>
        <td style="padding-left:6px;"><%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_PROD))%><input type="hidden" name="info<%=ItfConsts.TYPE_PROD%>" value=<%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_PROD))%>></td>
    </tr>
    <tr><td>3、</td>
        <td align="right">物料特征量同步</td>
        <td><input type="button" value="启动" onclick='doSubmit("start", "<%=ItfConsts.TYPE_PRODFEATURE%>")'/><input type="button" value="停止" onclick='doSubmit("stop", "<%=ItfConsts.TYPE_PRODFEATURE%>")'/><input type="button" value="重启" onclick='doSubmit("restart", "<%=ItfConsts.TYPE_PRODFEATURE%>")'/></td>
        <td style="padding-left:6px;"><%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_PRODFEATURE))%><input type="hidden" name="info<%=ItfConsts.TYPE_PRODFEATURE%>" value=<%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_PRODFEATURE))%>></td>
    </tr>
    <tr><td>4、</td>
        <td align="right">供应商主档案同步</td>
        <td><input type="button" value="启动" onclick='doSubmit("start", "<%=ItfConsts.TYPE_SUPPMAIN%>")'/><input type="button" value="停止" onclick='doSubmit("stop", "<%=ItfConsts.TYPE_SUPPMAIN%>")'/><input type="button" value="重启" onclick='doSubmit("restart", "<%=ItfConsts.TYPE_SUPPMAIN%>")'/></td>
        <td style="padding-left:6px;"><%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_SUPPMAIN))%><input type="hidden" name="info<%=ItfConsts.TYPE_SUPPMAIN%>" value=<%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_SUPPMAIN))%>></td>
    </tr>
    <tr><td>5、</td>
        <td align="right">供应商产品目录同步</td>
        <td><input type="button" value="启动" onclick='doSubmit("start", "<%=ItfConsts.TYPE_SUPPPRODCLASS%>")'/><input type="button" value="停止" onclick='doSubmit("stop", "<%=ItfConsts.TYPE_SUPPPRODCLASS%>")'/><input type="button" value="重启" onclick='doSubmit("restart", "<%=ItfConsts.TYPE_SUPPPRODCLASS%>")'/></td>
        <td style="padding-left:6px;"><%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_SUPPPRODCLASS))%><input type="hidden" name="info<%=ItfConsts.TYPE_SUPPPRODCLASS%>" value=<%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_SUPPPRODCLASS))%>></td>
    </tr>
    <tr><td colspan=4 height=30><b>电子商务读取供应商系统接口数据</b></td></tr>
    <tr><td>1、</td>
        <td align="right">供应商一年无交易数据</td>
        <td><input type="button" value="启动" onclick='doSubmit("start", "<%=ItfConsts.TYPE_SUPP_YNWJY%>")'/><input type="button" value="停止" onclick='doSubmit("stop", "<%=ItfConsts.TYPE_SUPP_YNWJY%>")'/><input type="button" value="重新读取" onclick='doSubmit("reread", "<%=ItfConsts.TYPE_SUPP_YNWJY%>")'/></td>
        <td style="padding-left:6px;"><%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_SUPP_YNWJY_INFO))%><input type="hidden" name="info<%=ItfConsts.TYPE_SUPP_YNWJY_INFO%>" value=<%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_SUPP_YNWJY_INFO))%>></td>
    </tr>
    <tr><td>2、</td>
        <td align="right">供应商综合评分数据</td>
        <td><input type="button" value="启动" onclick='doSubmit("start", "<%=ItfConsts.TYPE_SUPP_ZHPF%>")'/><input type="button" value="停止" onclick='doSubmit("stop", "<%=ItfConsts.TYPE_SUPP_ZHPF%>")'/><input type="button" value="重新读取" onclick='doSubmit("reread", "<%=ItfConsts.TYPE_SUPP_ZHPF%>")'/></td>
        <td style="padding-left:6px;"><font color=red>（百万级数据量，请慎重重新读取）</font><%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_SUPP_ZHPF_INFO))%><input type="hidden" name="info<%=ItfConsts.TYPE_SUPP_ZHPF_INFO%>" value=<%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_SUPP_ZHPF_INFO))%>></td>
    </tr>
    <tr><td colspan=4 height=30><b>定时任务</b></td></tr>
    <tr><td>1、</td>
        <td align="right">税率读取</td>
        <td><input type="button" value="启动" onclick='doSubmit("start", "<%=ItfConsts.TYPE_EXCHRATE%>")'/><input type="button" value="停止" onclick='doSubmit("stop", "<%=ItfConsts.TYPE_EXCHRATE%>")'/><input type="button" value="重启" onclick='doSubmit("restart", "<%=ItfConsts.TYPE_EXCHRATE%>")'/></td>
        <td style="padding-left:6px;"><%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_EXCHRATE))%><input type="hidden" name="info<%=ItfConsts.TYPE_EXCHRATE%>" value=<%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_EXCHRATE))%>></td>
    </tr-->

      <tr><td colspan=4 height=30><b>定时任务</b></td></tr>
      <tr><td>1、</td>
          <td align="right">多媒体文件转换</td>
          <td><input type="button" value="启动" onclick='doSubmit("start", "<%=ItfConsts.TYPE_EXCHRATE%>")'/><input type="button" value="停止" onclick='doSubmit("stop", "<%=ItfConsts.TYPE_EXCHRATE%>")'/><input type="button" value="重启" onclick='doSubmit("restart", "<%=ItfConsts.TYPE_EXCHRATE%>")'/></td>
          <td style="padding-left:6px;"><%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_EXCHRATE))%><input type="hidden" name="info<%=ItfConsts.TYPE_EXCHRATE%>" value=<%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_EXCHRATE))%>></td>
      </tr>

      <tr><td>2、</td>
          <td align="right">定时发布信息</td>
          <td><input type="button" value="启动" onclick='doSubmit("start", "<%=ItfConsts.TYPE_EXCHRATE%>")'/><input type="button" value="停止" onclick='doSubmit("stop", "<%=ItfConsts.TYPE_EXCHRATE%>")'/><input type="button" value="重启" onclick='doSubmit("restart", "<%=ItfConsts.TYPE_EXCHRATE%>")'/></td>
          <td style="padding-left:6px;"><%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_EXCHRATE))%><input type="hidden" name="info<%=ItfConsts.TYPE_EXCHRATE%>" value=<%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_EXCHRATE))%>></td>
      </tr>

      <tr><td>3、</td>
          <td align="right">定时清除未退出的用户</td>
          <td><input type="button" value="启动" onclick='doSubmit("start", "<%=ItfConsts.TYPE_EXCHRATE%>")'/><input type="button" value="停止" onclick='doSubmit("stop", "<%=ItfConsts.TYPE_EXCHRATE%>")'/><input type="button" value="重启" onclick='doSubmit("restart", "<%=ItfConsts.TYPE_EXCHRATE%>")'/></td>
          <td style="padding-left:6px;"><%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_EXCHRATE))%><input type="hidden" name="info<%=ItfConsts.TYPE_EXCHRATE%>" value=<%=StrUtil.toStr(request.getAttribute("info"+ ItfConsts.TYPE_EXCHRATE))%>></td>
      </tr>
  </table>
  </form>
  </body>
</html>
