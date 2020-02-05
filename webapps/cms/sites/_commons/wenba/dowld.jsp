<%@ page language="java" import="java.util.*,com.bizwink.wenba.*" contentType="text/html; charset=GBK"%>
<%
String pagenum = request.getParameter("thispage")==null?"0":request.getParameter("thispage");
String fenlei = request.getParameter("fenlei")==null?"":request.getParameter("fenlei");
String province = request.getParameter("province")==null?"":request.getParameter("province");
String keyword = request.getParameter("keyword")==null?"":request.getParameter("keyword");
List list = new ArrayList();
IWenbaManager iwenba = wenbaManagerImpl.getInstance();
list = iwenba.getWenti(fenlei,province,keyword);
List pagelist = new ArrayList();
		//总记录数
int allnum = list.size();
		//每页记录数
int perpagenum = 20;
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
		wenti wt = (wenti)list.get(i);
		pagelist.add(wt);
	}	
}else if(thispage>=allpages&&allpages>0){

	for(int i=(allpages-1)*perpagenum;i<list.size();i++){
		wenti wt = (wenti)list.get(i);
		pagelist.add(wt);
	}
}else if(thispage>=allpages&&allpages==0){
	for(int i=0;i<list.size();i++){
		String str = (String)list.get(i);
		pagelist.add(str);
	}
}else if(thispage==0&&allpages>1){
	for(int i=0;i<perpagenum;i++){
		wenti wt = (wenti)list.get(i);
		pagelist.add(wt);
	}
}else if(thispage==0&&allpages==1){
	for(int i=0;i<perpagenum;i++){
		wenti wt = (wenti)list.get(i);
		pagelist.add(wt);
	}
}
session.setAttribute("wldpagenum",String.valueOf(perpagenum));
session.setAttribute("wldpagelist",pagelist);
session.setAttribute("wldallpages",String.valueOf(allpages));
session.setAttribute("wldthispage",String.valueOf(thispage));
response.sendRedirect("wenba_wolaida.jsp");
%>