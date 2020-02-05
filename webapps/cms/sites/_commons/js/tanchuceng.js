function divtanchu(imgid,saleprice,originalprice,maintitle,imgsrc){
	var leftjuan = parseInt(document.body.scrollLeft);//网页被卷去的宽度
	var x = parseInt(document.body.scrollLeft+event.clientX);//x
	var y = parseInt(document.body.scrollTop+event.clientY);//y
	var screenwidth = parseInt(document.body.offsetWidth );//width
	var imgwidth = parseInt(document.getElementById(imgid).width);


  	var distance = 310;
	var srcpath = imgsrc;//document.getElementById(imgid).src;
	var fromleft = 0;
	var fromtop = 0;
	if(x+(imgwidth+310)<screenwidth+leftjuan){
		fromleft = x + imgwidth +10;
	}else{
		fromleft = x - imgwidth - distance - 10;
	}
	fromtop = document.body.scrollTop + 100;
    var w = document.createElement("div");
    w.setAttribute("id","mybody");
    var t = "<table width=\"301\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"+
			"      <tr>"+
			"	     <td><img src=\"space.gif\" height=\"9\"/></td>"+
			"		 <td width=\"272\"></td>"+
			"		 <td width=\"11\"></td>"+
			"	  </tr>"+
			"	  <tr>"+
			"	    <td width=\"18\"><img src=\"space.gif\"  width=\"7\"/></td>"+
			"		<td><img src=\""+srcpath+"\" width=\"272\" height=\"313\" /></td>"+
			"		<td></td>"+
			"	  </tr>"+
			"	  <tr>"+
			"	    <td></td>"+
			"		<td height=\"30\" align=\"center\" valign=\"middle\" style=\"font-size:14px;\"><strong>"+maintitle+"</strong></td>"+
			"		<td></td>"+
			"	  </tr>"+
			"	  <tr>"+
			"	    <td></td>"+
			"		<td height=\"20\" align=\"left\" valign=\"middle\" style=\"color:#A10000; font-weight:bold;\">售价："+saleprice+"</td>"+
			"		<td></td>"+
			"	  </tr>"+
			"	  <tr>"+
			"	    <td></td>"+
			"		<td height=\"20\" align=\"left\" valign=\"middle\" style=\"color:#999999\">原价："+originalprice+"</td>"+
			"		<td></td>"+
			"	  </tr>"+
			"	  <tr>"+
			"	    <td></td>"+
			"		<td align=\"left\" valign=\"middle\" style=\"color:#999999\">内容：</td>"+
			"		<td></td>"+
			"	  </tr>"+
			"	</table>";
  	w.innerHTML = t;
    w.style.position = 'absolute';
    w.style.zIndex = '10000';
    w.style.width = "301px";
    w.style.height ="480px";
    w.style.left = fromleft;
    w.style.top =fromtop;
    w.style.background= "url('/_sys_images/bg12-24-1.png')";
     
    document.body.appendChild(w);
}
function divquxiao(){
    document.body.removeChild(mybody);
}