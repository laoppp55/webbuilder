// JavaScript Document
$(document).ready(function(){
/*	$('#login_reg').bind('click', function() {
	  location.href="/reg.shtml";
	});*/
	$('#product_div').bind('mouseover', function() {
	  $(this).css("background","url(/images/job_bg_04_03.jpg)");
	  $('#line_1').css("border-left","#fff 1px dotted");
	});
	$('#product_div').bind('mouseout', function() {
	  $(this).css("background","url()");
	  $('#line_1').css("border-left","#999 1px dotted");
	});
	$('#news_div').bind('mouseover', function() {
	  $(this).css("background","url(/images/job_bg_04_04.jpg)");
	  $('#line_1').css("border-left","#fff 1px dotted");
	  $('#line_2').css("border-left","#fff 1px dotted");
	});
	$('#news_div').bind('mouseout', function() {
	  $(this).css("background","url()");
	  $('#line_1').css("border-left","#999 1px dotted");
	  $('#line_2').css("border-left","#999 1px dotted");
	});
	$('#offers_div').bind('mouseover', function() {
	  $(this).css("background","url(/images/job_bg_04_05.jpg)");
	  $('#line_2').css("border-left","#fff 1px dotted");
	});
	$('#offers_div').bind('mouseout', function() {
	  $(this).css("background","url()");
	  $('#line_2').css("border-left","#999 1px dotted");
	});
	
	
	//banner幻灯片
$('#slides1').slidesjs({
        width: 1280,
        height: 417,
        navigation: false,
        start: 1,
        play: {
          auto: true,
		  effect: "fade",
		  swap: false,
		  pauseOnHover: false
        },
        pagination: {
			active: false,
          effect: "fade"
        },
        effect: {
          fade: {
            speed: 400
          }
        }
     });
});
