<%@ page language="java" import="java.util.*,com.bizwink.wenba.*,com.bizwink.util.*,java.sql.*,com.bizwink.user.*" contentType="text/html;charset=GBK"%>
<%@ page import="java.net.*" %>
<%
	int ID = Integer.parseInt((String)request.getParameter("id"));
	int userid = Integer.parseInt((String)request.getParameter("uid"));
	int classid = ParamUtil.getIntParameter(request,"cid",24);
	String fenLei = (String)request.getParameter("fenlei");
	int qid =Integer.parseInt((String)request.getParameter("qid"));
	IWenbaManager iwenba = wenbaManagerImpl.getInstance();
	
	vote vo = new vote();
	vo = iwenba.getVote(ID);
	
	if(userid==vo.getUserID()){
		response.sendRedirect("/wenba/wenba_vote_e.jsp?cid="+classid);
	}else{
		iwenba.changePinglunNum(ID);
		vote vot = new vote();
		vot.setAnwID(ID);
		vot.setUserID(userid);
		iwenba.addVote(vot);
		response.sendRedirect("/wenba/wenba_finsh_vote.jsp?cid="+classid+"&fenlei=" + URLEncoder.encode(fenLei)+"&id=" + qid);
		//response.sendRedirect("/wenba/wenba_finsh.jsp?cid="+classid+"&fenlei=" + URLEncoder.encode(fenLei)+"&id=" + qid);
	}
%>
