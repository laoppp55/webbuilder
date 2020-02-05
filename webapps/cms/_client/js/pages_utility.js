// JavaScript Document
$(document).ready(function(){
/*	$('#login_reg').bind('click', function() {
	  location.href="/reg.shtml";
	});*/
	//banner幻灯片
$('#slides1').slidesjs({
        width: 1280,
        height: 271,
        navigation: false,
        start: 1,
        play: {
          auto: true,
		  effect: "fade",
		  swap: false,
		  pauseOnHover: false
        },
        pagination: {
          effect: "fade"
        },
        effect: {
          fade: {
            speed: 800
          }
        }
     });
});
