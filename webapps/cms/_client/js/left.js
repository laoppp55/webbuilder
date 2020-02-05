var num = 0;
$(function(){
	var $key = getQueryString('key');
	if($key){
		if($key == "webmenu"){
			li_red(getQueryString('k'),0);
		}else if($key == "menu"){
			li_menu(getQueryString('i'),getQueryString('k'));
		}else if($key == "footmenu"){
			li_footmenu(getQueryString('i'),getQueryString('k'));
		}

	}

	if(num == 0){
		$('#webmenu > li').each(function(i){   //首页导航
			var $a1 = $(this).children("a");
			$a1.attr("href",$a1.attr("href")+"?i=0&k="+i+"&key=webmenu");
		});
		$('#menu > li').each(function(k){    //左侧导航
			var $a = $(this).children("a");
			var $str = 0;
			if(getQueryString('key')=="webmenu"){
				$str = getQueryString('k');
			}else if(getQueryString('key')=="menu"){
				$str = getQueryString('i');
			}
			$a.attr("href",$a.attr("href")+"?i="+$str+"&k="+k+"&key=menu");
		});
		$('#wezmenu > a').each(function(i){			// 页面中文路径
			var $a1 = $(this);
			if(i==0){
				$a1.attr("href",$a1.attr("href")+"?i=0&k="+i+"&key=webmenu");
			}else{
				$a1.attr("href",$a1.attr("href")+"?i="+getQueryString('i')+"&k="+getQueryString('k')+"&key="+getQueryString('key')+"");
			}
		});
		$('#wzmenu > li').each(function(i){			//页面文章列表
			var $a1 = $(this).children("a");		
			$a1.attr("href",$a1.attr("href")+"?i="+getQueryString('i')+"&k="+getQueryString('k')+"&key="+getQueryString('key')+"");			
		});
		$('.nar_2 > p').each(function(i){			//
		var $a1 = $(this).children("a");		
			$a1.attr("href",$a1.attr("href")+"?i="+getQueryString('i')+"&k="+getQueryString('k')+"&key="+getQueryString('key')+"");			
		});
		$('.nar_1 > p').each(function(i){			//
		var $a1 = $(this).children("a");		
			$a1.attr("href",$a1.attr("href")+"?i="+getQueryString('i')+"&k="+getQueryString('k')+"&key="+getQueryString('key')+"");			
		});
		$('#page_nv > a').each(function(i){			//页面文章列表导航条
			var $a1 = $(this);		
			$a1.attr("href",$a1.attr("href")+"?i="+getQueryString('i')+"&k="+getQueryString('k')+"&key="+getQueryString('key')+"");			
		});
		$('#leftlink > li').each(function(i){			// 友链 联系 法律
			var $a1 = $(this).children("a");
			$a1.attr("href",$a1.attr("href")+"?i=0&k="+i+"&key=footmenu");
		});
		$('#foot_right > a').each(function(i){			// 页尾
			var $a1 = $(this);
			$a1.attr("href",$a1.attr("href")+"?i=0&k="+i+"&key=footmenu");
		});
		$('#foot_menu > a').each(function(i){			// 页面 友链 联系 法律  中文路径
			var $a1 = $(this);
			if(i==0){
				$a1.attr("href",$a1.attr("href")+"?i=0&k="+i+"&key=webmenu");
			}else{
				$a1.attr("href",$a1.attr("href")+"?i="+getQueryString('i')+"&k="+getQueryString('k')+"&key="+getQueryString('key')+"");
			}
		});
		num = 1;
	}
});

function getQueryString(name) {    
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");    
	var r = window.location.search.substr(1).match(reg);   
	if (r != null) 
		return unescape(r[2]); 
	return null;    
}

function li_menu(i,k){
	var $li = $($('#menu > li')[k]);
	if($li.length > 0){
		$li.attr("class","selectL");
		var $li1 = $($li.children('ul').find("li")[k]);
		if($li1.length > 0){
			$li1.removeAttr("class");
			$li1.attr("class","selectL");
		}
	}
	var $webli = $($('#webmenu > li')[i]);
	if($webli.length > 0){
		$webli.attr("class","selectA");
		var $webli1 = $($webli.children('ul').find("li")[k]);
		if($webli1.length > 0){
			$webli1.removeAttr("class");
			$webli1.attr("class","selectA");
		}
	}
	
}
function li_red(i,k){
	var $webli = $($('#webmenu > li')[i]);
	if($webli.length > 0){
		if(i==0){
			$webli.attr("class","selectAS");			
		}else{
			$webli.attr("class","selectA");
		}
		var $webli1 = $($webli.children('ul').find("li")[k]);
		if($webli1.length > 0){			
			$webli1.removeAttr("class");
			if(i==0){
				$webli.attr("class","selectAS");			
			}else{
				$webli.attr("class","selectA");
			}
		}
	}
	var $li = $($('#menu > li')[k]);
	if($li.length > 0){
		$li.attr("class","selectL");
		var $li1 = $($li.children('ul').find("li")[i]);
		if($li1.length > 0){
			$li1.removeAttr("class");
			$li1.attr("class","selectL");
		}
	}
	
}
function li_footmenu(i,k){
	var $li = $($('#leftlink > li')[k]);

	if($li.length > 0){
		$li.attr("class","selectL");
		var $li1 = $($li.children('ul').find("li")[i]);
		if($li1.length > 0){
			$li1.removeAttr("class");
			$li1.attr("class","selectL");
		}
	}
	
}




