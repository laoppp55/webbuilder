search('qt', 'ulSearchList', 'searchBtn');
// 搜索调用
function go() {
    var qt = $("#qt").val();
    if (qt == null || qt === ""  ) {
        alert("请输入搜索条件");
        return;
    }
    var url ="http://www.bjsjs.gov.cn/sitesearch/sitesearch?qt="+qt;
    window.open(url, "_self");
}

$(function(){
    //顶通banner
    $(".u-top-close").on("click",function(){
        $(".g-top-banner").hide();
    })


    //导航
    $(".g-nav li").on("mouseover",function(){
        var i = $(".g-nav li").index(this);
        $(".g-nav li").eq(i).find(".m-nav-secondary").show();
        $(".g-nav li").eq(i).find(".u-nav-links").addClass("u-nav-select");
    });
    $(".g-nav li").on("mouseout",function(){
        var i = $(".g-nav li").index(this);
        $(".g-nav li").eq(i).find(".m-nav-secondary").hide();
        $(".g-nav li").eq(i).find(".u-nav-links").removeClass("u-nav-select");
    });

    // 首屏要闻tab切换
    $(".m-one-news_nav li").on("mouseover",function(){
        var i = $(".m-one-news_nav li").index(this);
        $(".m-one-news_nav a").eq(i).addClass("f-select").parent().siblings().find("a").removeClass("f-select");
        $(".m-one-news ul").eq(i).addClass("f-show").siblings().removeClass("f-show");
    });

    // 首屏公开tab切换
    $(".m-one-news_nav2 li").on("mouseover",function(){
        var i = $(".m-one-news_nav2 li").index(this);
        $(".m-one-news_nav2 a").eq(i).addClass("f-select").parent().siblings().find("a").removeClass("f-select");
        $(".m-one-news2 ul").eq(i).addClass("f-show").siblings().removeClass("f-show");
    });
    //公开-政策版块
    $(".zcSearch_title li").click(function(){
        $(this).addClass("on").siblings("li").removeClass("on");
        $(".m-zcSearch .zcSearch_con").eq($(this).index()).show().siblings("div").hide();
    });
    var numChoice1=1,numChoice2=1,numChoice3=1,numChoice4=1;
    function changeFun(docU, docD, Num){
        docU.on("click", function(){
            var _top = $(this).parent().find("ul").css("top");
            if (_top < "0px") {
                $(this).parent().find("ul").stop().animate({top:"+=34px"},100);
            }
            Num--;
            // console.log(Num);
            if (Num <= 1) {
                Num = 1;
                $(this).addClass("disabled");
            } else {
                $(this).removeClass("disabled");
            }

            var valueTxt = $(this).siblings(".woshi_Box").find("li").eq(Num).html();
            // console.log(valueTxt);
            if (valueTxt == "全部") {
                $(this).siblings(".woshi_Box").find("input").val("");
            } else {
                $(this).siblings(".woshi_Box").find("input").val(valueTxt);
            }
        });
        docD.on("click", function(){
            var _topNum = Math.abs(parseInt($(this).parent().find("ul").css("top")));
            var _height = $(this).parent().find("ul").height();
            var Dvalue = _height-_topNum;
            if (Dvalue > 102) {
                $(this).parent().find("ul").stop().animate({top:"-=34px"},100);
                Num++;
            }
            // console.log(Num);
            if (Num > 0) {
                $(".zc_up_btn").removeClass("disabled");
            }
            var valueTxt = $(this).siblings(".woshi_Box").find("li").eq(Num).html();
            $(this).siblings(".woshi_Box").find("input").val(valueTxt);
        });
    }
    changeFun($("#zc_up_btn1"),$("#zc_down_btn1"),numChoice1);
    changeFun($("#zc_up_btn2"),$("#zc_down_btn2"),numChoice2);
    changeFun($("#zc_up_btn3"),$("#zc_down_btn3"),numChoice3);
    changeFun($("#zc_up_btn4"),$("#zc_down_btn4"),numChoice4);


    // 首屏三张图
    var _one_imgs = $(".m-one-photo-box img").length;
    switch(_one_imgs){
        case 1:
            $(".m-one-photo-box img").css({"width":"1160px"});
            break;
        case 3:
            $(".m-one-photo-box img").css({"width":"376px"});
            break;
        default:
            return false;
    }

});