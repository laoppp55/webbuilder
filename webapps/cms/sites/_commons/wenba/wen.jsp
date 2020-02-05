<%@ page  import="java.util.*,com.bizwink.wenba.*,com.bizwink.util.*,java.sql.*,com.bizwink.user.*" contentType="text/html;charset=GBK"%>
<%@ page import="java.net.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.ParsePosition" %>
<%
	wenbaImpl firstcolumn = null,sc=null;
    int classid = ParamUtil.getIntParameter(request,"cid",24);
    int locationid = ParamUtil.getIntParameter(request,"localid",0);
    IWenbaManager iwenba = wenbaManagerImpl.getInstance();
%>
<%
	
	//获取当前系统时间  用于计算距离回答结束还有多久
	
%>

<html>
<head></head>
<body>
<table>
<%
	List alllist = iwenba.getAllQuestions0(classid,0);
	String  wtdate = "";
	for(int l=0;l<alllist.size();l++){
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	Date systemDate = new Date();
	ParsePosition pos1 = new ParsePosition(0);
	ParsePosition pos2 = new ParsePosition(0);
	String SystemDate = sdf.format(systemDate);
	Date systemdate = sdf.parse(SystemDate, pos1);
		wenti wt_status = new wenti();
		int id = Integer.parseInt((String)alllist.get(l));
		wt_status = iwenba.getQuestion(id);
%>

<%	
		wtdate = wt_status.getCreatedate().toString();
		int wtposi = wtdate.indexOf(" ");
		wtdate = wtdate.substring(0, wtposi);
		//获取提问时间用于计算距离回答还有多少天
		Date cDate = sdf.parse(wtdate, pos2);
		long day = systemdate.getTime() - cDate.getTime();
		String days = String.valueOf((day / (24 * 60 * 60 * 1000)));
		int szts = 15;
		//计算距离回答结束的天数
		int dates = szts - Integer.parseInt(days);
		if(dates==0){
			iwenba.changeQuestionStatus(id);
			if(wt_status.getAnwsernum()==0){
				iwenba.changeanwStatus_wenti(id);//设置问题过期
			}else{
				int aid = iwenba.getOneansid(id).getId();
				iwenba.changeAnwStatus(aid);//选择得票数最多的答案为最佳
				
			}
		}
	
%>

<tr>
	<td><%= wtdate%></td>
</tr>
<% }%>
</table>
</body>
</html>
