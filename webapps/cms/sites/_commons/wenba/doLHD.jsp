<%@ page language="java" import="java.util.*,com.bizwink.wenba.*" contentType="text/html; charset=GBK"%>
<%
String pagenum = request.getParameter("thispage")==null?"0":request.getParameter("thispage");
String fenlei = request.getParameter("fenlei")==null?"0":request.getParameter("fenlei");
String province = request.getParameter("province")==null?"":request.getParameter("province");
String keyword = request.getParameter("keyword")==null?"":request.getParameter("keyword");
List list = new ArrayList();
IWenbaManager iwenba = wenbaManagerImpl.getInstance();
list = iwenba.getLHD(Integer.parseInt(fenlei),province,keyword);
List pagelist = new ArrayList();
		//总记录数
int allnum = list.size();
		//每页记录数
int perpagenum = 15;
		//每页条数
int allpages = allnum%perpagenum==0?allnum/perpagenum:allnum/perpagenum+1;
		//当前页
int thispage = Integer.parseInt(pagenum);

if(thispage<1){
	thispage = 1;
}else if(thispage>allpages){
	thispage = allpages;
}
if(thispage>0&&thispage<allpages){
	for(int i=(thispage-1)*perpagenum;i<thispage*perpagenum;i++){
		WenbaBean wt = (WenbaBean)list.get(i);
		pagelist.add(wt);
	}	
}else if(thispage>=allpages&&allpages>0){

	for(int i=(allpages-1)*perpagenum;i<list.size();i++){
		WenbaBean wt = (WenbaBean)list.get(i);
		pagelist.add(wt);
	}
}else if(thispage>=allpages&&allpages==0){
	for(int i=0;i<list.size();i++){
		String str = (String)list.get(i);
		pagelist.add(str);
	}
}else if(thispage==0&&allpages>1){
	for(int i=0;i<perpagenum;i++){
		WenbaBean wt = (WenbaBean)list.get(i);
		pagelist.add(wt);
	}
}else if(thispage==0&&allpages==1){
	for(int i=0;i<perpagenum;i++){
		WenbaBean wt = (WenbaBean)list.get(i);
		pagelist.add(wt);
	}
}
if(fenlei.equals(""))
	fenlei = "-@";
if(province.equals(""))
	province = "-@";
if(keyword.equals(""))
	keyword = "-@";
String str3 = province + "," + fenlei + "," +keyword;
session.setAttribute("str3",str3);
session.setAttribute("linghdpagenum",String.valueOf(perpagenum));
session.setAttribute("linghdpagelist",pagelist);
session.setAttribute("linghdallpages",String.valueOf(allpages));
session.setAttribute("linghdthispage",String.valueOf(thispage));
session.setAttribute("lhdcid",fenlei);
response.sendRedirect("wenba_LHD.jsp");
%>