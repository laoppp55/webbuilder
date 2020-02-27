$(function(){
	$("#_ul li").each(function(a,i){
				$(this).click(function(){
					$("#_ul li").each(function(m,n){
						$(this).removeAttr("class");
						$("#_div"+m).css("display","none");
					});						
					$(this).attr("class","ts");
					$("#_div"+a).css("display","block");
				});
			});
});	