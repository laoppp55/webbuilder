<%@ page import="com.bizwink.cms.news.*,
		com.bizwink.cms.security.*,
		 com.bizwink.cms.util.*" contentType="text/html;charset=utf-8"%>
<%
	// creation success variable:
	boolean errors;
	boolean success = false;
	// get parameters
	boolean doUpdate  = ParamUtil.getBooleanParameter(request,"doUpdate");
	int     articleID = ParamUtil.getIntParameter(request, "article", 0);
	String maintitle =null, vicetitle=null, content =null, summary =null, keyword =null, source = null;
	int docLevel = 0, sortid=0;
	String year=null,month=null,day=null;

	// creating the article
	IArticleManager articleManager = null;
	articleManager = ArticlePeer.getInstance();
	Article article = null;
	article = articleManager.getArticle(articleID);

	int columnID = article.getColumnID();
	IColumnManager columnManager = ColumnPeer.getInstance();
	Column column = columnManager.getColumn(columnID);
	String columnName = column.getCName();
	int auditLevel = column.getAuditLevel();
	int newAuditLevel = article.getAuditFlag()+1;
	String rights = "audit"+ newAuditLevel;
	Auth authToken = SessionUtil.getUserAuthorization( request,  response, session);
	if ( authToken != null && !SecurityCheck.hasPermission(authToken,rights) ) {
		response.sendRedirect(
				response.encodeRedirectURL("articles.jsp?column="+columnID+"&msg=Now Your Audit Level Rights is Lower than Needed!")
		);
		return;
	}
	if (!ColumnCheck.hasPermission(authToken,columnID)) {
		response.sendRedirect(
				response.encodeRedirectURL("articles.jsp?column="+columnID+"&msg=You have not right to do this in this channel!")
		);
		return;
	}
	if(doUpdate ) {
		// get an article manager to update article
		try {
			//pubflag: 0表示还未审核，1表示已开始审核了,2标识审核过了,3 表示已经发布
			// auditflag 99
			article.setPubFlag(1);
			article.setAuditFlag(98);
			articleManager.update(article);
			success = true;
		}
		catch( ArticleException e ) {
			errors = true;
		}
	}

	// if a column was successfully created, say so and return (to stop the
	// jsp from executing
	if( success ) {
		response.sendRedirect(
				response.encodeRedirectURL("articles.jsp?column="+article.getColumnID()+"&msg=This Leve Audit Passed!")
		);
		return;
	}
	maintitle     = article.getMainTitle();
	vicetitle     = article.getViceTitle();
	content   = article.getContent();
	content   = StringUtil.replace(content, "\r","");
	content   = StringUtil.replace(content, "\n","");
	content   = StringUtil.replace(content, "\"","&quot;");
	summary   = article.getSummary();
	keyword   = article.getKeyword();
	docLevel  = article.getDocLevel();
	source    = article.getSource();
	sortid    = article.getSortID();
	year = article.getPublishTime().toString().substring(0,4);
	month = article.getPublishTime().toString().substring(5,7);
	day = article.getPublishTime().toString().substring(8,10);

%>
<html>
<head>
	<meta http-equiv=Content-Type content="text/html; charset=utf-8">
	<link REL="stylesheet" TYPE="text/css" HREF="../style/global.css">
	<script ID="clientEventHandlersJS" LANGUAGE="javascript">
        <!--
        function audit() {
            createForm.submit();
            return true;
        }
        //-->
	</SCRIPT>
</head>
<body onload="  Composition.document.body.innerHTML='<%=content%>';">
<%
	String[][] titlebars = {
			{ "文章管理", "articlesmain.jsp" },
			{ columnName, "articles.jsp?column="+columnID },
			{"编辑文章", ""}
	};
	String[][] operations = null;
%>
<%@ include file="../inc/titlebar.jsp" %>
<form action="returnarticle.jsp" method="post"  name=createForm target="temp">
	<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width=100%>
		<input type="hidden" name="doUpdate" value="true">
		<input type="hidden" name="article" value="<%=articleID%>">
		<tr><td align=left class=line colspan=2>文章录入信息</td></tr>
		<tr><td class=line><font class=line>主标题：</font>
			<input class=tine name=maintitle size=30 value="<%= (maintitle!=null)?maintitle:"" %>">*
			副标题：<input class=tine name=vicetitle size=30 value="<%= (vicetitle!=null)?vicetitle:"" %>">
		</td></tr>
		<tr><td class=line>摘要：
			<input class=tine name=summary size=30  value="<%= (summary!=null)?summary:"" %>">
			关键字：<input class=tine name=keyword size=30  value="<%= (keyword!=null)?keyword:"" %>">(多个以;隔开)
		</td></tr>
		<tr><td align=left class=line colspan=2>
			来源：<input class=tine name=source size=30  value="<%= (source!=null)?source:"" %>">
			发布日期:<input  class=tine type=text size=3 maxlength=4 name=year   value="<%= (year!=null)?year:"" %>">年
			<select name=month size=1 class=tine>
				<option value=''></option><option value=1  <%= (month.compareTo("01")==0)?"selected":"" %>>1</option>
				<option value=2  <%= (month.compareTo("02")==0)?"selected":"" %>>2</option>
				<option value=3  <%= (month.compareTo("03")==0)?"selected":"" %>>3</option>
				<option value=4  <%= (month.compareTo("04")==0)?"selected":"" %>>4</option>
				<option value=5  <%= (month.compareTo("05")==0)?"selected":"" %>>5</option>
				<option value=6  <%= (month.compareTo("06")==0)?"selected":"" %>>6</option>
				<option value=7  <%= (month.compareTo("07")==0)?"selected":"" %>>7</option>
				<option value=8  <%= (month.compareTo("08")==0)?"selected":"" %>>8</option>
				<option value=9  <%= (month.compareTo("09")==0)?"selected":"" %>>9</option>
				<option value=10  <%= (month.compareTo("10")==0)?"selected":"" %>>10</option>
				<option value=11  <%= (month.compareTo("11")==0)?"selected":"" %>>11</option>
				<option value=12  <%= (month.compareTo("12")==0)?"selected":"" %>>12</option>
			</select>月
			<select name=day size=1 class=tine>
				<option value=""></option><option value=1  <%= (day.compareTo("01")==0)?"selected":"" %>>1</option>
				<option value=2 <%= (day.compareTo("02")==0)?"selected":"" %>>2</option>
				<option value=3 <%= (day.compareTo("03")==0)?"selected":"" %>>3</option>
				<option value=4 <%= (day.compareTo("04")==0)?"selected":"" %>>4</option>
				<option value=5 <%= (day.compareTo("05")==0)?"selected":"" %>>5</option>
				<option value=6 <%= (day.compareTo("06")==0)?"selected":"" %>>6</option>
				<option value=7 <%= (day.compareTo("07")==0)?"selected":"" %>>7</option>
				<option value=8 <%= (day.compareTo("08")==0)?"selected":"" %>>8</option>
				<option value=9 <%= (day.compareTo("09")==0)?"selected":"" %>>9</option>
				<option value=10 <%= (day.compareTo("10")==0)?"selected":"" %>>10</option>
				<option value=11 <%= (day.compareTo("11")==0)?"selected":"" %>>11</option>
				<option value=12 <%= (day.compareTo("12")==0)?"selected":"" %>>12</option>
				<option value=13 <%= (day.compareTo("13")==0)?"selected":"" %>>13</option>
				<option value=14 <%= (day.compareTo("14")==0)?"selected":"" %>>14</option>
				<option value=15 <%= (day.compareTo("15")==0)?"selected":"" %>>15</option>
				<option value=16 <%= (day.compareTo("16")==0)?"selected":"" %>>16</option>
				<option value=17 <%= (day.compareTo("17")==0)?"selected":"" %>>17</option>
				<option value=18 <%= (day.compareTo("18")==0)?"selected":"" %>>18</option>
				<option value=19 <%= (day.compareTo("19")==0)?"selected":"" %>>19</option>
				<option value=20 <%= (day.compareTo("20")==0)?"selected":"" %>>20</option>
				<option value=21 <%= (day.compareTo("21")==0)?"selected":"" %>>21</option>
				<option value=22 <%= (day.compareTo("22")==0)?"selected":"" %>>22</option>
				<option value=23 <%= (day.compareTo("23")==0)?"selected":"" %>>23</option>
				<option value=24 <%= (day.compareTo("34")==0)?"selected":"" %>>24</option>
				<option value=25 <%= (day.compareTo("25")==0)?"selected":"" %>>25</option>
				<option value=26 <%= (day.compareTo("26")==0)?"selected":"" %>>26</option>
				<option value=27 <%= (day.compareTo("27")==0)?"selected":"" %>>27</option>
				<option value=28 <%= (day.compareTo("28")==0)?"selected":"" %>>28</option>
				<option value=29 <%= (day.compareTo("29")==0)?"selected":"" %>>29</option>
				<option value=30 <%= (day.compareTo("30")==0)?"selected":"" %>>30</option>
				<option value=31 <%= (day.compareTo("31")==0)?"selected":"" %>>31</option>
			</select>日
		</td></tr>
		<tr><td align=left class=line colspan=2>
			重要性：
			<input type=radio <%= (docLevel==0)? "checked":""  %> name="docLevel"  value="0">否
			<input type=radio <%= (docLevel==1)?"checked":"" %> name="docLevel" value="1">是
			&nbsp;&nbsp;&nbsp;文章的排序：<input class=tine name=sortid size=10  value="<%= sortid %>"></td></tr>
		<tr><td ID=bottomofFld></td></tr>
	</table>
</form>
<table width=100%>
	<tr>
		<td width=10%>
			&nbsp;
		</td>
		<td>
			<IFRAME class="Composition" width="100%" ID="Composition" height="320"></IFRAME>
		</td>
		<SCRIPT language = "javascript">
            <!--
            Composition.document.open();
            Composition.document.write("");
            Composition.document.close();
            Composition.document.designMode="On";
            Composition.focus();
            setTimeout("Composition.focus()",0)
            // -->
		</SCRIPT>
		<td width=5%>
			&nbsp;
		</td>
	</tr>
	<tr>
		<td colspan=3 align=center>
			<input type=image src=../images/button_modi.gif onclick="audit()">&nbsp;
			<input type=image src=../images/button_cancel.gif onclick="javascript:window.close()">
		</td>
	</tr>
</table>

</body>
</html>
