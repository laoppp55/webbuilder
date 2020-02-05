<table width="982" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="225"><img src="/images/wenba_r4_c2.jpg" width="225" height="27" /></td>
    <td align="center" background="/images/wenba_r4_c3.jpg"><SPAN id=imgtou1></SPAN>&nbsp;<a href="/wenba/wenba_difang.jsp?cid=<%= classid%>&pro=北京&sdsd=1" class="bai">地方问答</a></td>
    <td width="1" align="center" background="/images/wenba_r4_c3.jpg"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center" background="/images/wenba_r4_c3.jpg"><SPAN id=imgtou2></SPAN>&nbsp;<a href="/wenba/wenba_leibiao.jsp?cid=<%= classid%>&keys=""&sdsd=2 " class="bai">最新提出问题</a></td>
    <td width="1" align="center" background="/images/wenba_r4_c3.jpg"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center" background="/images/wenba_r4_c3.jpg"><SPAN id=imgtou3></SPAN>&nbsp;<a href="/wenba/wenba_leibiao1.jsp?cid=<%= classid%>&keys=""&sdsd=3 " class="bai">最新解决问题</a></td>
    <td width="1" align="center" background="/images/wenba_r4_c3.jpg"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center" background="/images/wenba_r4_c3.jpg"><SPAN id=imgtou4></SPAN>&nbsp;<a href="/wenba/wenba_leibiao0a.jsp?cid=<%= classid%>&keys=""&sdsd=4 " class="bai">零回答问题</a></td>
    <td width="1" align="center" background="/images/wenba_r4_c3.jpg"><img src="/images/wenba_r5_c5.jpg" width="1" height="12" align="absmiddle" /></td>
    <td align="center" background="/images/wenba_r4_c3.jpg"><SPAN id=imgtou5></SPAN>&nbsp;<a href="/wenba/wenba_zhuanjia.jsp?cid=<%= classid%>&sdsd=5" class="bai">专家介绍</a></td>
    <td width="192"><img src="/images/wenba_r4_c6.jpg" width="192" height="27" /></td>
  </tr>
</table>
<SCRIPT language=javascript>
var search=location.search.slice(1);//得到get方式提交的查询字符串 
var arr=search.split("&"); 
for(var i=0;i<arr.length;i++){ 
var ar=arr[i].split("="); 
if(ar[0]=="sdsd"){ 
var img=document.getElementById("imgtou"+ar[1]).innerHTML="<IMG height=7 src=/webbuilder/sites/www_fawu_com/images/wenba__c5.jpg width=4 align=absMiddle> ";
} 
} 
</SCRIPT>