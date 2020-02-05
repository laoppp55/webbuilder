/**
 * Created by Administrator on 2016/12/15.
 */
$(function(){
    $('input, textarea').placeholder();
    /*登录tab页转换*/
    $(".content-list").click(function(){
        $(".content-list").removeClass("content-list-select");
        $(this).addClass("content-list-select");
        var index=$(this).index();
        $(".login-name").addClass("all-block-none");
        $(".login-name:eq("+index+")").removeClass("all-block-none")
    })
    /*控制input框中颜色的变化，如果是placeholder内容的话，颜色为浅灰色，否则为黑色*/
    $("input").bind("focus",function(){
        $(this).css("color","#666");
    }).bind("blur",function(){
        var placetext=$(this).attr("placeholder");
        if($(this).val()!=""){
            if($(this).val()!=placetext){
                $(this).css("color","#666");
            }else{
                $(this).css("color","#999");
            }
        }else{
            $(this).css("color","#999");
        }
    })

    /*导航栏的经过和点击之后背景色的变化*/
    /*给点击的元素添加选中标志*/
    $(".nav-list").on("click",function(){
        $(".nav-list").removeClass("navselectflag");
        $(this).addClass("navselectflag");
    })
    /*鼠标进入元素 删除其他元素的选中状态 给当前元素添加选中状态的类名*/
    $(".nav-list").on("mouseover",function(){
        $(".nav-selected").removeClass("nav-selected");
        $(this).addClass("nav-selected");
    });
    /*鼠标离开 将选中状态类名删除*/
    $(".nav-list").on("mouseout",function(){
        $(".nav-selected").removeClass("nav-selected");
    });
    /*鼠标离开导航栏 给有选中标志的添加选中状态的类名*/
    $(".nav-lists").on("mouseleave",function(){
        $(".navselectflag").addClass("nav-selected")
    })

})